package merakhel.fantasy.ui.myaccounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import merakhel.fantasy.network.IApiMethod
import merakhel.fantasy.network.RequestModel
import merakhel.fantasy.network.WebServiceClient
import merakhel.fantasy.R
import merakhel.fantasy.ui.dashboard.MyAccountFragment
import merakhel.fantasy.ui.home.models.UsersPostDBResponse
import merakhel.fantasy.utils.MyPreferences
import merakhel.fantasy.utils.MyUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import merakhel.fantasy.databinding.FragmentPlayingHistoryBinding


class PlayingHistoryFragment(myAccountFragment: MyAccountFragment) : Fragment() {

    private var mBinding: FragmentPlayingHistoryBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mBinding  = DataBindingUtil.inflate(inflater,
            R.layout.fragment_playing_history, container, false)

        return mBinding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding!!.progressBarMatches.visibility = View.GONE
        getPlayingMatchHistory()
    }

    fun getPlayingMatchHistory() {
        if(!MyUtils.isConnectedWithInternet(activity as AppCompatActivity)) {
            MyUtils.showToast(activity as AppCompatActivity,"No Internet connection found")
            return
        }
        var models = RequestModel()
        models.user_id = MyPreferences.getUserID(requireActivity())!!
        WebServiceClient(requireActivity()).client.create(IApiMethod::class.java).getPlayingMatchHistory(models)
            .enqueue(object : Callback<UsersPostDBResponse?> {
                override fun onFailure(call: Call<UsersPostDBResponse?>?, t: Throwable?) {

                }

                override fun onResponse(
                    call: Call<UsersPostDBResponse?>?,
                    response: Response<UsersPostDBResponse?>?
                ) {
                    if(isAdded) {
                        var res = response!!.body()
                        if(res!=null) {
                            var responseModel = res.responseObject
                            if(responseModel!=null) {
                                var totalTeamJoined = responseModel.totalTeamJoined
                                var totalContestJoined = responseModel.totalContestJoined
                                var totalMatchPlayed = responseModel.totalMatchPlayed
                                var totalMatchWin = responseModel.totalMatchWin
                                mBinding!!.txtMatchPlayed.setText(String.format("%d",totalMatchPlayed))
                                mBinding!!.txtContestPlayed.setText(String.format("%d",totalContestJoined))
                                mBinding!!.txtContestWin.setText(String.format("%d",totalMatchWin))
                                mBinding!!.totalBalance.setText(String.format("%d",totalMatchPlayed))

                            }
                        }
                    }


                }

            })

    }



}
