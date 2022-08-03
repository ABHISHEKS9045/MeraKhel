package merakhel.fantasy.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import merakhel.fantasy.R


class CustomeProgressDialog(context: Context?) : Dialog(context!!) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
        setCancelable(false)
        setCanceledOnTouchOutside(false)

       /* val doubleBounce: Sprite = DoubleBounce()
        spin_kit.setIndeterminateDrawable(doubleBounce)*/
    }

}