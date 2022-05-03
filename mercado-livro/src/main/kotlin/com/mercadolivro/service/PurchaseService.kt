package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.events.listener.PurchaseEvent
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val bookService: BookService
){

    fun create(purchaseModel: PurchaseModel) {
        purchaseModel.books
            .filter { BookStatus.ATIVO != bookService.findById(it.id!!.toInt()).status }
            .map { throw NotFoundException(Errors.ML301.message.format(it.id), Errors.ML301.code) }

        purchaseRepository.save(purchaseModel)
        println("Disparando evento de compra")
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
        println("Finalização do processamento!")
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }
}
