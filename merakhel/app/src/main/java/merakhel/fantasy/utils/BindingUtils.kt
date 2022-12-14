package merakhel.fantasy.utils

import android.os.CountDownTimer
import android.util.Log
import merakhel.fantasy.listener.OnMatchTimerStarted
import merakhel.fantasy.BuildConfig
import java.text.SimpleDateFormat
import java.util.*


class BindingUtils {

    companion object {
        private var isCountedObjectCreated: Boolean=false
        var timer: CountDownTimer?=null
        val MATCH_STATUS_UPCOMING: Int=1
        val MATCH_STATUS_COMPLETED: Int=2
        val MATCH_STATUS_LIVE: Int=3

        val BANNERS_KEY_REFFER:String? = "reffer"
        val BANNERS_KEY_SUPPORT:String? = "support"
        val BANNERS_KEY_BROWSERS:String? = "browser"
        val EMAIL: String?="info@merakhel.com"
        val PHONE_NUMBER: String?="9045624217 "
        val GOOGLE_TEZ_PACKAGE_NAME = "https://business.paytm.com/payment-gateway"
//        val PAYMENT_GOOGLEPAY_UPI = "durgeshmandal1@oksbi"
//        val PAYMENT_GOOGLEPAY_UPI = "9820500905@okbizaxis"
        val PAYMENT_GOOGLEPAY_UPI = "pavanbhatia@okicici"

//        val PAYMENT_GOOGLEPAY_UPI = "lakhanpatidar003@okaxis"
//        val PAYMENT_Paytm_UPI ="9045624217@paytm"
        val BASE_URL  ="https://merakhel.com/"
//        https://merakhel.com/
//        val BASE_URL  ="https://skoreon.com/fantasy/"
        val WEBVIEW_FANTASY_POINTS  ="https://merakhel.com/fantasy-points-system-mobile/"
        val WEBVIEW_FANTASY_HOW_TO_PLAY  ="https://merakhel.com/how-to-play-mobile/"
        val WEBVIEW_TNC  ="https://merakhel.com/terms-and-conditions/"
        val WEBVIEW_CHAT  ="https://sportsfight.in/liveChat"
        val WEBVIEW_PRIVACY  ="https://merakhel.com/privacy-policy/"
        val WEBVIEW_ABOUT_US  ="https://merakhel.com/about-us/"
        val WEBVIEW_LEGALITY  ="https://merakhel.com/legality-mobile/"
        const val NOTIFICATION_ID_BIG_IMAGE = 101
        val BILTY_APK_LINK: String = "https://merakhel.com/public/upload/apk/merakhel.com.apk"

        var currentTimeStamp : Long=0

        fun getAppVersion():  String{
            return BuildConfig.VERSION_NAME
        }

        fun getDayName():String{
            val now = Date()
            val dateFormat =
                SimpleDateFormat("EEE", Locale.US)
            val asWeek = dateFormat.format(now)
            return asWeek;
        }
        fun getCurrentDate(): String {
            val df = SimpleDateFormat("yyyy-MM-dd")
            var dateobj = Date()
            return  df.format(dateobj)
        }

        private fun yesterday(): Date {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -1)
            return cal.time
        }

        private fun getBeforYesterDay(): Date {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -2)
            return cal.time
        }
//        fun logD(tag: String, message: String) {
//            if (BuildConfig.MLOG) {
//                Log.e("MX:$tag", message)
//            }
//        }

  //Ciyagarh , Ganesh mala signal

        fun countDownStart(
            starttimeStamp:Long,
            listeners: OnMatchTimerStarted
        ) {
            if (starttimeStamp > currentTimeStamp && !isCountedObjectCreated){
                isCountedObjectCreated  = true
                //BindingUtils.logD("TimerLogs","Count Down timer Called")
                timer = object: CountDownTimer(starttimeStamp, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val itemLong = starttimeStamp;
                        val date = Date(itemLong * 1000L)
                        //val date2 = Date(currentTimeStamp*1000L)
                        val date2 = Date()
                        if (date2.before(date)){
                            val l8 = date.time - date2.time
                            val l2 = l8 / 86400000L
                            java.lang.Long.signum(l2)
                            val l3 = l8 - 86400000L * l2
                            val l4 = l3 / 3600000L
                            val l5 = l3 - 3600000L * l4
                            try {
                                val l6 = l5 / 60000L
                                val l7 = (l5 - 60000L * l6) / 1000L
                                //val customTextView2: CustomTextView = customTextView
                                val stringBuilder = StringBuilder()
                                if(l2!=0L) {
                                    stringBuilder.append(l2)
                                    stringBuilder.append("d ")
                                }

                                if(l4!=0L) {
                                    stringBuilder.append(l4)
                                    stringBuilder.append("h ")
                                }
                                if(l6!=0L) {
                                    stringBuilder.append(l6)
                                    stringBuilder.append("m ")
                                }
                                stringBuilder.append(l7)
                                stringBuilder.append("s left")
                                // upcomingMatchesAdapter.notifyItemChanged(viewType)
                                listeners.onTicks(stringBuilder.toString())
                            } catch (exception: Exception) {
                               // MyUtils.logd("timestamp",exception.message)
                                exception.printStackTrace()
                            }
                        }else {
                            listeners.onTimeFinished()
                        }

                    }

                    override fun onFinish() {
                        listeners.onTimeFinished()
                        isCountedObjectCreated = false
                    }
                }
                timer!!.start()
            }else {
                listeners.onTimeFinished()
                isCountedObjectCreated = false
            }

        }

        fun stopTimer(){
            if(timer!=null) {
                isCountedObjectCreated = false
                timer!!.cancel()
                timer=null
               // logD("TimerLogs","Timer Cancelled ")
            }
        }



        fun countDownStartForAdaptors(
            starttimeStamp:Long,
            listeners: OnMatchTimerStarted
        ) {
            if (starttimeStamp > currentTimeStamp){
                //BindingUtils.logD("TimerLogs","Count Down timer Called Adaptors")
                var timerAdapters = object : CountDownTimer(starttimeStamp, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val itemLong = starttimeStamp;
                        val date = Date(itemLong * 1000L)
                        //val date2 = Date(currentTimeStamp*1000L)
                        val date2 = Date()
                        if (date2.before(date)) {
                            val l8 = date.time - date2.time
                            val l2 = l8 / 86400000L
                            java.lang.Long.signum(l2)
                            val l3 = l8 - 86400000L * l2
                            val l4 = l3 / 3600000L
                            val l5 = l3 - 3600000L * l4
                            try {
                                val l6 = l5 / 60000L
                                val l7 = (l5 - 60000L * l6) / 1000L
                                //val customTextView2: CustomTextView = customTextView
                                val stringBuilder = StringBuilder()
                                if (l2 != 0L) {
                                    stringBuilder.append(l2)
                                    stringBuilder.append("d ")
                                }

                                if (l4 != 0L) {
                                    stringBuilder.append(l4)
                                    stringBuilder.append("h ")
                                }
                                if (l6 != 0L) {
                                    stringBuilder.append(l6)
                                    stringBuilder.append("m ")
                                }
                                stringBuilder.append(l7)
                                stringBuilder.append("s left")
                                // upcomingMatchesAdapter.notifyItemChanged(viewType)
                               //logD("TimerLogs","Adaptor:"+stringBuilder.toString())
                                listeners.onTicks(stringBuilder.toString())
                            } catch (exception: Exception) {
                               // MyUtils.logd("TimerLogs", exception.message)
                                exception.printStackTrace()
                            }
                        } else {
                            listeners.onTimeFinished()
                        }

                    }

                    override fun onFinish() {
                        listeners.onTimeFinished()
                    }
                }
                timerAdapters!!.start()
            }else {
                listeners.onTimeFinished()
            }

        }
    }


}
