import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import kotlin.math.roundToInt

fun Double.roundTwoDecimals(): Double {
    return (this * 100).roundToInt() / 100.0
}

class ClaseDeTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    describe("Dado un conjunto de objetos de ejemplo") {
        val pelota = Pelota(radio = 4)
        val cubo = Pieza(volumen = 4)
        val cilindro = Pieza(volumen = 3, color = "rosa")
        val baldecito = Baldecito(costoFijo = 5, piezas = mutableListOf(cubo, cilindro))
        val iniciales = mutableListOf(Pieza(volumen = 6), Pieza(volumen = 5), Pieza(volumen = 4))
        val tachito = Tachito(costoFijo = 3, piezas = iniciales)
        val todosLosJuguetes = mutableListOf(pelota, baldecito, tachito)
        val valentin = Ninie(edadEnMeses = 10, juguetes = todosLosJuguetes)
        val zoe = Curiose(edadEnMeses = 20, juguetes = mutableListOf(pelota, baldecito))
        val milena = Revoltose(edadEnMeses = 15)

        it("el costo de fabricación de la pelota es 24") {
            pelota.costoFabricacion() shouldBe 24
        }

        it("el costo de fabricación del baldecito es 21") {
            baldecito.costoFabricacion() shouldBe 21
        }
        it("el costo de fabricación del tachito es 39") {
            tachito.costoFabricacion() shouldBe 39
        }
        it("la eficacia de la pelota es 12") {
            pelota.eficacia() shouldBe 12
        }
        it("la eficacia del baldecito es 6") {
            baldecito.eficacia() shouldBe 6
        }
        it("la eficacia del tachito es 15") {
            tachito.eficacia() shouldBe 15
        }
        it("el precio de la pelota es 144") {
            pelota.precioVenta() shouldBe 144
        }
        it("el precio del baldecito es 101") {
            baldecito.precioVenta() shouldBe 101
        }
        it("el precio del tachito es 189") {
            tachito.precioVenta() shouldBe 189
        }

        it("el tiempo de entretenimiento de la pelota para valentin es 13.2") {
            valentin.tiempoDeEntretenimiento(pelota).roundTwoDecimals() shouldBe 13.2
        }
        it("el tiempo de entretenimiento de la pelota para zoe es 21.6") {
            zoe.tiempoDeEntretenimiento(pelota).roundTwoDecimals() shouldBe 21.6
        }
        it("el tiempo de entretenimiento de la pelota para milena es 6") {
            milena.tiempoDeEntretenimiento(pelota).roundTwoDecimals() shouldBe 6
        }
        it("valentin acepta cualquier juguete") {
            todosLosJuguetes.all { juguete: Juguete -> valentin.aceptaJuguete(juguete) } shouldBe true
        }
        it("zoe acepta la pelota y el baldecito") {
            todosLosJuguetes.filter { juguete: Juguete -> zoe.aceptaJuguete(juguete) } shouldContainExactlyInAnyOrder mutableListOf(
                baldecito,
                pelota,
            )
        }
        it("milena acepta solo el tachito") {
            todosLosJuguetes.filter { juguete: Juguete -> milena.aceptaJuguete(juguete) } shouldContainExactlyInAnyOrder mutableListOf(
                tachito,
            )
        }
        it("valentin le dona a milena") {
            valentin.donarJuguetes(milena)
            valentin.juguetes shouldContainExactlyInAnyOrder mutableListOf(pelota, baldecito)
            milena.juguetes shouldContainExactlyInAnyOrder mutableListOf(tachito)
        }


    }
})