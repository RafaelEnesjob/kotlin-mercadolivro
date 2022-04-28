package com.mercadolivro.controller

import com.mercadolivro.controller.mapper.PurchasseMapper
import com.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("purchase")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchasseMapper: PurchasseMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody request: PostPurchaseRequest) {
        purchaseService.create(purchasseMapper.toModel(request))
    }
}