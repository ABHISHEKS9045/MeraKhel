package merakhel.fantasy.ui.dashboard

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd

import com.google.android.material.tabs.TabLayout
import merakhel.fantasy.*
import merakhel.fantasy.models.UserInfo
import merakhel.fantasy.models.WalletInfo
import merakhel.fantasy.ui.myaccounts.MyAccountBalanceFragment
import merakhel.fantasy.ui.myaccounts.PlayingHistoryFragment
import merakhel.fantasy.ui.myaccounts.TransactionFragment
import merakhel.fantasy.R
import merakhel.fantasy.databinding.FragmentMyaccountsBinding
import merakhel.fantasy.utils.MyPreferences
import merakhel.fantasy.utils.MyUtils

class MyAccountFragment : Fragment() {

    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var walletInfo: WalletInfo
    private lateinit var userInfo: UserInfo
    private var mBinding: FragmentMyaccountsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_myaccounts, container, false
        )
        return mBinding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userInfo = (requireActivity().applicationContext as SportsFightApplication).userInformations
        walletInfo = (requireActivity().applicationContext as SportsFightApplication).walletInfo
        (activity as MainActivity)



        val viewpager: ViewPager = view.findViewById(R.id.account_viewpager)
        val tabs: TabLayout = view.findViewById(R.id.account_tabs)
        setupViewPager(viewpager)
        // viewpager.addOnPageChangeListener(this)
        tabs.setupWithViewPager(viewpager)

//        mInterstitialAd = newInterstitialAd()
        loadInterstitial()
    }

//    private fun newInterstitialAd(): InterstitialAd {
//        val interstitialAd = InterstitialAd(context)
//        interstitialAd.adUnitId = getString(R.string.interstitial_ad_unit_id)
//        interstitialAd.adListener = object : AdListener()
//        {
//            override fun onAdLoaded() {
//            }
//
//            override fun onAdFailedToLoad(errorCode: Int) {
//            }
//
//            override fun onAdClosed() {
//                tryToLoadAdOnceAgain()
//                val intent = Intent(activity!!, EditProfileActivity::class.java)
//                startActivity(intent)
//            }
//        }
//        return interstitialAd
//    }

    private fun loadInterstitial() {
        // Disable the load ad button and load the ad.
        val adRequest = AdRequest.Builder().build()
//        mInterstitialAd!!.loadAd(adRequest)
    }

    private fun showInterstitial() {
        // Show the ad if it is ready. Otherwise toast and reload the ad.
//        if (mInterstitialAd != null && mInterstitialAd!!.isLoaded)
        {
//            mInterstitialAd!!.show()
//        } else {
            // Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
//            tryToLoadAdOnceAgain()
            if (Build.VERSION.SDK_INT > 20) {
//                val intent = Intent(activity!!, EditProfileActivity::class.java)
//                startActivity(intent)
            }

        }
    }

    private fun tryToLoadAdOnceAgain() {
//        mInterstitialAd = newInterstitialAd()
        loadInterstitial()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        initProfile()
    }
    override fun onResume() {
        super.onResume()
        initProfile()
    }

    public fun initProfile() {
        if(!isVisible){
            return
        }
        if(userInfo!=null) {
            mBinding!!.profileName.setText(userInfo.fullName)
            Glide.with(requireActivity())
                .load(MyPreferences.getProfilePicture(requireActivity()))
                .placeholder(R.drawable.dummy)
                .into(mBinding!!.profileImg)
        }else {
            mBinding!!.profileName.setText("GUEST")
        }

        mBinding!!.btnEditProfile.setOnClickListener(View.OnClickListener {

            showInterstitial()

        })

        if(walletInfo!=null){
            var accountStatus = walletInfo.accountStatus
            if(accountStatus!=null){
                if(walletInfo.bankAccountVerified==3){
                    mBinding!!.btnVerifyAccount.setText("REJECTED")
                    mBinding!!.btnVerifyAccount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.0f)
                    mBinding!!.btnVerifyAccount.setBackgroundResource(R.drawable.button_selector_red)
                    mBinding!!.btnVerifyAccount.setTextColor(Color.WHITE)
                    mBinding!!.btnVerifyAccount.setOnClickListener(View.OnClickListener {
                        gotoDocumentsListActivity()
                    })

                }else
                if(walletInfo.bankAccountVerified==2){
                    mBinding!!.btnVerifyAccount.setText("Account Verified")
                    mBinding!!.btnVerifyAccount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.0f)
                    mBinding!!.btnVerifyAccount.setBackgroundResource(R.drawable.button_selector_green)
                    mBinding!!.btnVerifyAccount.setTextColor(Color.WHITE)
                    mBinding!!.btnVerifyAccount.setOnClickListener(View.OnClickListener {
                        gotoDocumentsListActivity()
                    })
                }else if(walletInfo.bankAccountVerified==1){
                    mBinding!!.btnVerifyAccount.setText("Approval Pending")
                    mBinding!!.btnVerifyAccount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.0f)
                    mBinding!!.btnVerifyAccount.setBackgroundResource(R.drawable.button_selector_white)
                    mBinding!!.btnVerifyAccount.setTextColor(Color.BLACK)
                    mBinding!!.btnVerifyAccount.setOnClickListener(View.OnClickListener {
                        gotoDocumentsListActivity()
                    })
                }else {
                    mBinding!!.btnVerifyAccount.setOnClickListener(View.OnClickListener {
                        val intent = Intent(requireActivity(), VerifyDocumentsActivity::class.java)
                        startActivityForResult(intent, VerifyDocumentsActivity.REQUESTCODE_VERIFY_DOC)
                    })
                }
            }
        }else {
            mBinding!!.btnVerifyAccount.setOnClickListener(View.OnClickListener {
                MyUtils.showToast(requireActivity() as AppCompatActivity,"verify clicked")
                val intent = Intent(requireActivity(), VerifyDocumentsActivity::class.java)
                startActivityForResult(intent, VerifyDocumentsActivity.REQUESTCODE_VERIFY_DOC)
            })
        }

    }

    private fun gotoDocumentsListActivity() {
        val intent = Intent(requireActivity(), DocumentsListActivity::class.java)
        startActivityForResult(intent, VerifyDocumentsActivity.REQUESTCODE_VERIFY_DOC)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = MyAccountViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(MyAccountBalanceFragment(this),"Balance")
        adapter.addFragment(PlayingHistoryFragment(this),"Playing History")
        adapter.addFragment(TransactionFragment(this),"Transaction")
        viewPager.adapter = adapter
    }


    internal inner class MyAccountViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }
}