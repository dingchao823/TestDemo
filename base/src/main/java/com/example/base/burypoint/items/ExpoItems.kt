package com.example.base.burypoint.items

import com.example.base.bean.Goods
import com.example.base.burypoint.*
import com.example.base.burypoint.bean.ExpoBean

fun Goods.expoStart(position : Int, params : Map<String, Any>?){
    if (params == null){
        return
    }
    // 商品自己组装数据
    val model = ExpoBean()
    model.expoId = this.productId
    model.commodityName = this.productName
    model.pricePerCommodity = this.price
    model.commodityRank = position
    model.expoEventName = "commodityShow"

    var it : Any? = params["pageType"]
    if (it is String) {
        model.pageType = it
    }
    it = params["prePosition"]
    if (it is String) {
        model.prePosition = it
    }
    it = params["page"]
    if (it is Int) {
        model.page = it
    }
    it = params["specialTopic"]
    if (it is Int) {
        model.specialTopic = it
    }
    it = params["activity"]
    if (it is String) {
        model.activity = it
    }
    it = params["floorName"]
    if (it is String) {
        model.floorName = it
    }
    it = params["floorRank"]
    if (it is Int) {
        model.floorRank = it
    }
    it = params["keyWord"]
    if (it is String) {
        model.keyWord = it
    }
    it = params["searchID"]
    if (it is String) {
        model.searchID = it
    }
    it = params["markCode"]
    if (it is String) {
        model.markCode = it
    }

    when(expoMode){
        is HandlerMode -> {
            ExpoHandlerHelper.instance.addExpoToMap(model)
        }
        is ThreadMode -> {
            ExpoThreadHelper.addExpoToMap(model)
        }
    }

}