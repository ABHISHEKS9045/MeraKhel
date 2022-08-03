package merakhel.fantasy.listener

import merakhel.fantasy.ui.contest.models.ContestModelLists

interface OnContestEvents {
    fun onContestJoinning(objects:ContestModelLists,position: Int)
    fun onShareContest(objects:ContestModelLists)
}