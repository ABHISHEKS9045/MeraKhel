package merakhel.fantasy.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import merakhel.fantasy.ui.contest.models.ContestModelLists
import java.io.Serializable


class ContestsParentModels:Serializable,Cloneable {

    var contestTitle : String=""
    var contestSubTitle : String=""

    @SerializedName("contests")
    @Expose
    var allContestsRunning: ArrayList<ContestModelLists> ?=null


}
