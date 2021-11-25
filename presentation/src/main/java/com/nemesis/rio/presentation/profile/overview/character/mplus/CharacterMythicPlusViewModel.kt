package com.nemesis.rio.presentation.profile.overview.character.mplus

import androidx.lifecycle.*
import com.chibatching.kotpref.bulk
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import com.nemesis.rio.domain.mplus.runs.sorting.MythicPlusRunsSortingOption
import com.nemesis.rio.domain.mplus.scores.usecase.GetExpansionsWithScores
import com.nemesis.rio.domain.mplus.scores.usecase.GetSeasonsWithScoresForExpansion
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.sorting.SortingOrder
import com.nemesis.rio.presentation.mplus.ranks.MythicPlusRanksType
import com.nemesis.rio.presentation.mplus.scores.MythicPlusScoresType
import com.nemesis.rio.presentation.profile.overview.character.mplus.ranks.CharacterMythicPlusRanksData
import com.nemesis.rio.presentation.profile.overview.character.mplus.ranks.CharacterMythicPlusRanksDataActionsHandler
import com.nemesis.rio.presentation.profile.overview.character.mplus.ranks.CharacterMythicPlusRanksDataFactory
import com.nemesis.rio.presentation.profile.overview.character.mplus.runs.CharacterMythicPlusRunsData
import com.nemesis.rio.presentation.profile.overview.character.mplus.runs.CharacterMythicPlusRunsDataActionsHandler
import com.nemesis.rio.presentation.profile.overview.character.mplus.runs.CharacterMythicPlusRunsDataFactory
import com.nemesis.rio.presentation.profile.overview.character.mplus.scores.CharacterMythicPlusScoresData
import com.nemesis.rio.presentation.profile.overview.character.mplus.scores.CharacterMythicPlusScoresDataActionsHandler
import com.nemesis.rio.presentation.profile.overview.character.mplus.scores.CharacterMythicPlusScoresDataFactory
import com.nemesis.rio.presentation.utils.LoadingStateController
import com.nemesis.rio.presentation.utils.LoadingStateDelegate
import com.nemesis.rio.presentation.utils.extensions.notNullValue
import com.nemesis.rio.presentation.utils.extensions.updateValue
import com.nemesis.rio.presentation.utils.mapWithDelayedLoading
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterMythicPlusViewModel(
    private val characterFlow: Flow<Character>,
    private val getExpansionsWithScores: GetExpansionsWithScores,
    private val getSeasonsWithScoresForExpansion: GetSeasonsWithScoresForExpansion,
    private val scoresDataFactory: CharacterMythicPlusScoresDataFactory,
    private val ranksDataFactory: CharacterMythicPlusRanksDataFactory,
    private val runsDataFactory: CharacterMythicPlusRunsDataFactory,
    private val openUrlInBrowserEventFlow: MutableSharedFlow<String>,
    private val loadingStateController: LoadingStateController
) : ViewModel(),
    DefaultLifecycleObserver,
    LoadingStateDelegate by loadingStateController,
    CharacterMythicPlusScoresDataActionsHandler,
    CharacterMythicPlusRanksDataActionsHandler,
    CharacterMythicPlusRunsDataActionsHandler {

    private val _characterMythicPlusData = MutableLiveData<CharacterMythicPlusData>()
    val characterMythicPlusData: LiveData<CharacterMythicPlusData> = _characterMythicPlusData

    private lateinit var selectedSeason: Season
    private lateinit var expansionsWithSeasons: Map<Expansion, List<Season>>
    private var selectedScoresType = CharacterMythicPlusPreferences.scoresType

    private var selectedRanksType = CharacterMythicPlusPreferences.ranksType
    private var selectedRanksScope = CharacterMythicPlusPreferences.ranksScope

    private var selectedRunsSortingOption = CharacterMythicPlusPreferences.runsSortingOption
    private var selectedRunsSortingOrder = CharacterMythicPlusPreferences.runsSortingOrder

    private val _characterMythicPlusOptionSelectEvent =
        MutableSharedFlow<CharacterMythicPlusOptionSelectEvent>()
    val characterMythicPlusOptionSelectEvent: Flow<CharacterMythicPlusOptionSelectEvent> =
        _characterMythicPlusOptionSelectEvent

    init {
        characterFlow
            .mapWithDelayedLoading(
                loadingStateController,
                viewModelScope,
                ::initializeMythicPlusData
            )
            .onEach(_characterMythicPlusData::setValue)
            .launchIn(viewModelScope)
    }

    private suspend fun initializeMythicPlusData(character: Character): CharacterMythicPlusData? {
        val scoresData: CharacterMythicPlusScoresData? = initializeScoresData(character)

        val ranksData = ranksDataFactory.getRanksDataForCurrentSeason(
            character,
            selectedRanksType,
            selectedRanksScope
        )

        val runsData = runsDataFactory.getMythicPlusRunsDataForCurrentSeason(
            character,
            selectedRunsSortingOption,
            selectedRunsSortingOrder
        )

        val characterHasMythicPlusData = scoresData != null || ranksData != null || runsData != null

        return if (characterHasMythicPlusData) {
            CharacterMythicPlusData(scoresData, ranksData, runsData)
        } else {
            null
        }
    }

    private suspend fun initializeScoresData(character: Character): CharacterMythicPlusScoresData? {
        val expansionsWithScores = getExpansionsWithScores(character)
        if (expansionsWithScores.isEmpty()) return null

        expansionsWithSeasons = expansionsWithScores.associateWith { expansion ->
            getSeasonsWithScoresForExpansion(character, expansion)
        }

        selectedSeason = expansionsWithSeasons.getValue(expansionsWithScores.first()).first()

        return scoresDataFactory.getScoresData(character, selectedScoresType, selectedSeason)
    }

    override fun onSelectScoresTypeClicked() {
        sendMythicPlusOptionSelectEvent {
            SelectScoresType(selectedScoresType)
        }
    }

    fun onScoresTypeChanged(scoresType: MythicPlusScoresType) {
        selectedScoresType = scoresType
        updateScoresData { character ->
            scoresDataFactory.getScoresData(character, selectedScoresType, selectedSeason)
        }
    }

    override fun onSelectSeasonClicked() {
        sendMythicPlusOptionSelectEvent {
            SelectScoresSeason(
                expansionsWithSeasons,
                selectedSeason
            )
        }
    }

    fun onSeasonChanged(season: Season) {
        selectedSeason = season
        updateScoresData { character ->
            scoresDataFactory.getScoresData(character, selectedScoresType, selectedSeason)
        }
    }

    override fun onRanksTypeClicked() {
        sendMythicPlusOptionSelectEvent { SelectRanksType(selectedRanksType) }
    }

    fun onRanksTypeChanged(ranksType: MythicPlusRanksType) {
        selectedRanksType = ranksType
        updateRanksData { character, oldRanksData ->
            ranksDataFactory.updateRanksType(character, ranksType, oldRanksData)
        }
    }

    override fun onRanksScopeClicked() {
        sendMythicPlusOptionSelectEvent {
            SelectRanksScope(selectedRanksScope, characterFlow.first().faction)
        }
    }

    fun onRanksScopeChanged(ranksScope: MythicPlusRanksScope?) {
        selectedRanksScope = ranksScope
        updateRanksData { character, oldRanksData ->
            ranksDataFactory.updateRanksScope(character, ranksScope, oldRanksData)
        }
    }

    override fun onRunsSortingOptionClicked() {
        sendMythicPlusOptionSelectEvent { SelectRunsSoringOption(selectedRunsSortingOption) }
    }

    override fun onRunsSortingOrderClicked() {
        sendMythicPlusOptionSelectEvent { SelectRunsSortingOrder(selectedRunsSortingOrder) }
    }

    fun onRunsSortingOrderChanged(sortingOrder: SortingOrder) {
        selectedRunsSortingOrder = sortingOrder
        updateRunsData { runsDataFactory.updateSortingOrder(sortingOrder, it) }
    }

    fun onRunsSortingOptionChanged(sortingOption: MythicPlusRunsSortingOption) {
        selectedRunsSortingOption = sortingOption
        updateRunsData { runsDataFactory.updateSortingOption(sortingOption, it) }
    }

    override fun onOpenRunInBrowserClicked(run: MythicPlusRun) {
        viewModelScope.launch { openUrlInBrowserEventFlow.emit(run.url) }
    }

    private inline fun sendMythicPlusOptionSelectEvent(
        crossinline block: suspend () -> CharacterMythicPlusOptionSelectEvent
    ) {
        viewModelScope.launch {
            _characterMythicPlusOptionSelectEvent.emit(block())
        }
    }

    private inline fun updateRanksData(
        crossinline block: suspend (character: Character, oldRanksData: CharacterMythicPlusRanksData) -> CharacterMythicPlusRanksData
    ) {
        viewModelScope.launch {
            val character = characterFlow.first()
            val oldRanksData = _characterMythicPlusData.notNullValue.ranksData!!
            val updatedRanksData = block(character, oldRanksData)
            _characterMythicPlusData.updateValue {
                it.copy(ranksData = updatedRanksData)
            }
        }
    }

    private inline fun updateScoresData(
        crossinline block: suspend (character: Character) -> CharacterMythicPlusScoresData
    ) {
        viewModelScope.launch {
            val character = characterFlow.first()
            val updatedScoresData = block(character)
            _characterMythicPlusData.updateValue {
                it.copy(scoresData = updatedScoresData)
            }
        }
    }

    private inline fun updateRunsData(block: (oldRunsData: CharacterMythicPlusRunsData) -> CharacterMythicPlusRunsData) {
        val oldRunsData = _characterMythicPlusData.notNullValue.runsData!!
        val updatedRunsData = block(oldRunsData)
        _characterMythicPlusData.updateValue { it.copy(runsData = updatedRunsData) }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        CharacterMythicPlusPreferences.bulk {
            scoresType = selectedScoresType
            ranksType = selectedRanksType
            ranksScope = selectedRanksScope
            runsSortingOption = selectedRunsSortingOption
            runsSortingOrder = selectedRunsSortingOrder
        }
    }
}
