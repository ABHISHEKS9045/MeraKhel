package merakhel.fantasy.ui.createteam

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import merakhel.fantasy.listener.OnTeamCreateListener
import merakhel.fantasy.R
import merakhel.fantasy.databinding.FragmentCreateTeamListBinding
import merakhel.fantasy.CreateTeamActivity
import merakhel.fantasy.models.UpcomingMatchesModel
import merakhel.fantasy.ui.createteam.adaptors.PlayersContestAdapter
import merakhel.fantasy.ui.createteam.models.PlayersInfoModel
import merakhel.fantasy.utils.CricketPlayersFilters
import merakhel.fantasy.utils.MyUtils


class Batsman(batsmen: ArrayList<PlayersInfoModel>,val matchObject: UpcomingMatchesModel,val playerType:Int) : Fragment() {
    var count=0
    private lateinit var mListener: OnTeamCreateListener
     var batsmen = batsmen
    private var mBinding: FragmentCreateTeamListBinding? = null
    lateinit var adapter: PlayersContestAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding  = DataBindingUtil.inflate(inflater,
            R.layout.fragment_create_team_list, container, false);
        return mBinding!!.getRoot();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding!!.labelPlayersCounts.setText("Select 3 - 6 Batsmen")
        batsmen = CricketPlayersFilters.getPlayersbyOddEvenPositions(batsmen,matchObject)
        mBinding!!.recyclerCreatePlayersList.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(
            mBinding!!.recyclerCreatePlayersList.getContext(),
            RecyclerView.VERTICAL
        )
        mBinding!!.recyclerCreatePlayersList.addItemDecoration(dividerItemDecoration)

        adapter = PlayersContestAdapter(
            requireActivity(),
            batsmen,
            matchObject,
            playerType
        )
        mBinding!!.recyclerCreatePlayersList.adapter = adapter

        adapter.onItemClick = { objects ->
            CreateTeamActivity.isEditMode = false
            if(objects.isSelected) {
                count--
                objects.isSelected = false
                mListener.onBatsManDeSelected(objects)
            }else{
                if(!CreateTeamActivity.isAllPlayersSelected!!) {
                    if(count < CreateTeamActivity.MAX_BATSMAN[1]) {
                        if(isMaxPlayersValid(objects)) {
                            if(isMinimumPlayerSelected()) {
                                count++
                                objects.isSelected = true
                                mListener.onBatsManSelected(objects)
                            }
                        }else {
                            MyUtils.showToast(requireActivity() as AppCompatActivity,"MAX Player Reached limit  "+objects.teamShortName)
                        }
                    }else {
                        MyUtils.showToast(requireActivity() as AppCompatActivity,"MAX ALLOWED is "+ CreateTeamActivity.MAX_BATSMAN[1])
                    }
                }else {
                    MyUtils.showToast(requireActivity() as AppCompatActivity,"ALL 11 Players Selected")
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun isMinimumPlayerSelected(): Boolean {
        if(CreateTeamActivity.totalPlayers>= CreateTeamActivity.MAX_PLAYERS_FROM_TEAM) {
            if (CreateTeamActivity.COUNT_WICKET_KEEPER < CreateTeamActivity.MAX_WICKET_KEEPER[0]) {
                MyUtils.showToast(requireActivity() as AppCompatActivity,"Minimum "+ CreateTeamActivity.MAX_WICKET_KEEPER[0]+" "+"Wicket Keeper Required")
                return false
            } else if (CreateTeamActivity.COUNT_BATS_MAN < CreateTeamActivity.MAX_BATSMAN[0]) {
                //MyUtils.showToast(activity!!.getWindow().getDecorView().getRootView(),"Minimum "+ CreateTeamActivity.MAX_BATSMAN[0]+" "+"BatsMan Required")
                return true
            } else if (CreateTeamActivity.COUNT_ALL_ROUNDER < CreateTeamActivity.MAX_ALL_ROUNDER[0]) {
                MyUtils.showToast(requireActivity() as AppCompatActivity,"Minimum "+ CreateTeamActivity.MAX_ALL_ROUNDER[0]+" "+"All Rounder Required")
                return false
            } else if (CreateTeamActivity.COUNT_BOWLER <  CreateTeamActivity.MAX_BOWLER[0]) {
                MyUtils.showToast(requireActivity() as AppCompatActivity,"Minimum "+ CreateTeamActivity.MAX_BOWLER[0]+" "+"BOWLER Required")
                return false

            }
            return true
        }
        return true
    }
    private fun isMaxPlayersValid(objects: PlayersInfoModel): Boolean {
        if(objects.teamId == CreateTeamActivity.teamAId && CreateTeamActivity.TEAMA < CreateTeamActivity.MAX_PLAYERS_FROM_TEAM){
            return true
        }else if(objects.teamId == CreateTeamActivity.teamBId && CreateTeamActivity.TEAMB < CreateTeamActivity.MAX_PLAYERS_FROM_TEAM){
            return true
        }
        return false
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnTeamCreateListener) {
            mListener = context as OnTeamCreateListener
        } else {
            throw RuntimeException(
                "$context must implement OnTeamCreateListener"
            )
        }

    }

}
