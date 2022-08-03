package merakhel.fantasy.listener

interface OnPageRefreshedListener {
    fun onPageCreated(pageName:String)
    fun onRefreshed(pageName:String)
}