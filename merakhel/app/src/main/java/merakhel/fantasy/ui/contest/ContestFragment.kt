package merakhel.fantasy.ui.contest

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import merakhel.fantasy.listener.OnContestEvents
import merakhel.fantasy.listener.OnContestLoadedListener
import merakhel.fantasy.CreateTeamActivity
import merakhel.fantasy.WebActivity
import merakhel.fantasy.R
import merakhel.fantasy.databinding.FragmentAllContestBinding
import merakhel.fantasy.models.ContestsParentModels
import merakhel.fantasy.models.UpcomingMatchesModel
import merakhel.fantasy.network.IApiMethod
import merakhel.fantasy.network.RequestModel
import merakhel.fantasy.network.WebServiceClient
import merakhel.fantasy.ui.contest.adaptors.ContestAdapter
import merakhel.fantasy.ui.home.models.UsersPostDBResponse
import merakhel.fantasy.utils.BindingUtils
import merakhel.fantasy.utils.MyPreferences
import merakhel.fantasy.utils.MyUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContestFragment(
    val objectMatches: UpcomingMatchesModel?
) : Fragment(){

    var matchObject: UpcomingMatchesModel?=null
    var mListenerContestEvents: OnContestEvents?=null
    private lateinit var mListener: OnContestLoadedListener
    private var mBinding: FragmentAllContestBinding? = null
    lateinit var adapter: ContestAdapter
    var checkinArrayList = ArrayList<ContestsParentModels>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding  = DataBindingUtil.inflate(inflater,
            R.layout.fragment_all_contest, container, false);
        return mBinding!!.getRoot();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchObject = objectMatches
        mBinding!!.contestViewRecycler.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = ContestAdapter(
            requireActivity(),
            checkinArrayList,
            matchObject,
            mListenerContestEvents!!
        )


        mBinding!!.linearEmptyContest.visibility=View.GONE
        mBinding!!.contestViewRecycler.adapter = adapter

        mBinding!!.btnCreateTeam.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, CreateTeamActivity::class.java)
            intent.putExtra(CreateTeamActivity.SERIALIZABLE_MATCH_KEY,matchObject)
            requireActivity().startActivityForResult(intent, CreateTeamActivity.CREATETEAM_REQUESTCODE)
        })



        mBinding!!.btnEmptyView.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, WebActivity::class.java)
            intent.putExtra(WebActivity.KEY_URL,BindingUtils.WEBVIEW_PRIVACY)
            requireActivity().startActivity(intent)
        })
        mBinding!!.contestRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            getAllContest()
        })

    }

    override fun onResume() {
        super.onResume()
        if(!MyUtils.isConnectedWithInternet(activity as AppCompatActivity)) {
            MyUtils.showToast(activity as AppCompatActivity,"No Internet connection found")
            return
        }
        getAllContest()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnContestLoadedListener) {
            mListener = context as OnContestLoadedListener
        }else {
            throw RuntimeException(
                "$context must implement OnContestLoadedListener"
            )
        }

        if (context is OnContestEvents) {
            mListenerContestEvents = context as OnContestEvents
        }else {
            throw RuntimeException(
                "$context must implement OnContestLoadedListener"
            )
        }

    }

    fun getAllContest() {
        //var userInfo = (activity as PlugSportsApplication).userInformations
        mBinding!!.contestRefresh.isRefreshing=true
        var models = RequestModel()
        models.user_id = MyPreferences.getUserID(requireActivity())!!
        models.token =MyPreferences.getToken(requireActivity())!!
        models.match_id = "" + matchObject!!.matchId
        WebServiceClient(requireActivity()).client.create(IApiMethod::class.java).getContestByMatch(models)
            .enqueue(object : Callback<UsersPostDBResponse?>{
                override fun onFailure(call: Call<UsersPostDBResponse?>?, t: Throwable?) {
                    MyUtils.showToast(activity!! as AppCompatActivity,t!!.localizedMessage)
                    mBinding!!.contestRefresh.isRefreshing=false
                }

                override fun onResponse(
                    call: Call<UsersPostDBResponse?>?,
                    response: Response<UsersPostDBResponse?>?
                ) {
                    mBinding!!.contestRefresh.isRefreshing=false
                    var res = response!!.body()

                    if(res!=null) {
                        BindingUtils.currentTimeStamp =  res.systemTime
                        var responseModel = res.responseObject
                        Log.e(TAG, "onResponse: GetContest"+res )
                        if(responseModel!!.matchContestlist!=null && responseModel!!.matchContestlist!!.size>0) {
                            checkinArrayList.clear()
                            checkinArrayList.addAll(responseModel!!.matchContestlist!!)
                            adapter!!.setMatchesList(checkinArrayList)
                            mListener.onMyTeam(responseModel!!.myjoinedTeams!!)
                            mListener.onMyContest(responseModel!!.joinedContestDetails!!)
                        }else {
                            MyUtils.showToast(activity!! as AppCompatActivity,"No Contest available for this match "+res.toString())
                        }
                    }
                    updateEmptyViews()
                }

            })

    }


    fun updateEmptyViews(){
        if(checkinArrayList.size==0){
            mBinding!!.linearEmptyContest.visibility=View.VISIBLE
        }else {
            mBinding!!.linearEmptyContest.visibility=View.GONE
        }
    }



}
