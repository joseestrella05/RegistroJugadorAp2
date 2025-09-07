package edu.ucne.RegistroJugadorAp2.domain.usecase

data class ValidationResult(
    val isValid: Boolean,
    val nombresError: String? = null,
    val partidasError: String? = null
)

fun validateJugadorUi(nombres: String, partidas: String): ValidationResult {
    if (nombres.isBlank()) return ValidationResult(false, nombresError = "El nombre es requerido")
    if (partidas.isBlank()) return ValidationResult(false, partidasError = "Las partidas son requeridas")
    val partidasNum = partidas.toIntOrNull()
    if (partidasNum == null || partidasNum <= 0) return ValidationResult(false, partidasError = "Debe ser mayor que 0")
    return ValidationResult(true)
}
