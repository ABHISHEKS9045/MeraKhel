package merakhel.fantasy.listener

import merakhel.fantasy.models.MyTeamModels
import merakhel.fantasy.ui.contest.models.ContestModelLists

interface OnContestLoadedListener {
    fun onMyContest(contestModel: ArrayList<ContestModelLists>)
    fun onMyTeam(count: ArrayList<MyTeamModels>)
}