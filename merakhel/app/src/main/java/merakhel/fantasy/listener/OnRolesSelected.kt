package merakhel.fantasy.listener

import merakhel.fantasy.ui.createteam.models.PlayersInfoModel

interface OnRolesSelected {
    fun onTrumpSelected(objects: PlayersInfoModel,position: Int)
    fun onCaptainSelected(objects: PlayersInfoModel,position: Int)
    fun onViceCaptainSelected(objects: PlayersInfoModel,position: Int)
    fun onReady()

}