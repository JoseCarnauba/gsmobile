package br.com.fiap.models

data class EnergyConsumptionModel(
    val type: String,
    val consumption: Double,
    val period: String
)