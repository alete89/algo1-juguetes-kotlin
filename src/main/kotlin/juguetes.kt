abstract class Juguete() {

    companion object {
        var CUV = 2
    }

    abstract fun costoFabricacion(): Int

    abstract fun eficacia(): Int

    open fun precioVenta(): Int {
        return this.eficacia() * 10 + this.costoFabricacion()
    }
}

class Pelota(private val radio: Int) : Juguete() {
    override fun costoFabricacion(): Int {
        return this.radio * 3 * CUV
    }

    override fun eficacia(): Int {
        return 12
    }

}

abstract class ConPiezas(private val costoFijo: Int, val piezas: MutableList<Pieza> = mutableListOf()) : Juguete() {
    override fun costoFabricacion(): Int {
        return this.costoFijo + this.piezaDeMayorVolumen().costoFabricacion() * this.cantidadDePiezas()
    }

    fun cantidadDePiezas(): Int {
        return this.piezas.size
    }

    private fun piezaDeMayorVolumen(): Pieza {
        return this.piezas.maxBy { pieza: Pieza -> pieza.volumen }
    }

    private fun hayPiezaRosa(): Boolean {
        return this.piezas.any { pieza: Pieza -> pieza.esRosa() }
    }

    override fun precioVenta(): Int {
        return super.precioVenta() + if (this.hayPiezaRosa()) 20 else 0
    }

}

class Baldecito(costoFijo: Int, piezas: MutableList<Pieza>) : ConPiezas(costoFijo, piezas) {
    companion object {
        val minutosFijos = 3
    }

    override fun eficacia(): Int {
        return minutosFijos * this.cantidadDePiezas()
    }

}

class Tachito(costoFijo: Int, piezas: MutableList<Pieza>) : ConPiezas(costoFijo, piezas) {

    override fun eficacia(): Int {
        return this.piezas.sumOf { pieza: Pieza -> pieza.eficacia() }
    }

}

class Pieza(val volumen: Int, private val color: String = "azul") : Juguete() {

    override fun costoFabricacion(): Int {
        return this.volumen * CUV
    }

    override fun eficacia(): Int {
        return this.volumen
    }

    fun esRosa(): Boolean {
        return this.color == "rosa"
    }

}

open class Ninie(val edadEnMeses: Int, val juguetes: MutableList<Juguete> = mutableListOf()) {
    open fun tiempoDeEntretenimiento(juguete: Juguete): Double {
        val ef = juguete.eficacia()
        val ce = this.coeficienteEntretenimiento()
        return ef * ce
    }

    private fun coeficienteEntretenimiento(): Double {
        val res = 1 + (this.edadEnMeses.toDouble() / 100)
        return res
    }

    open fun aceptaJuguete(juguete: Juguete): Boolean {
        return true
    }

    fun recibirJuguete(juguete: Juguete) {
        this.juguetes.add(juguete)
    }

    fun donarJuguetes(otre: Ninie) {
        this.juguetes.forEach { juguete: Juguete ->
            if (otre.aceptaJuguete(juguete)) {
                otre.recibirJuguete(juguete)
            }
        }
        this.juguetes.removeIf { juguete: Juguete -> otre.aceptaJuguete(juguete) }
    }

}

class Curiose(edadEnMeses: Int, juguetes: MutableList<Juguete> = mutableListOf()) : Ninie(edadEnMeses, juguetes) {
    override fun tiempoDeEntretenimiento(juguete: Juguete): Double {
        val sup = super.tiempoDeEntretenimiento(juguete)
        return sup * 1.5
    }

    override fun aceptaJuguete(juguete: Juguete): Boolean {
        return juguete.precioVenta() < 150
    }
}

class Revoltose(edadEnMeses: Int, juguetes: MutableList<Juguete> = mutableListOf()) : Ninie(edadEnMeses, juguetes) {
    override fun tiempoDeEntretenimiento(juguete: Juguete): Double {
        return juguete.eficacia().toDouble() / 2
    }

    override fun aceptaJuguete(juguete: Juguete): Boolean {
        val te = this.tiempoDeEntretenimiento(juguete)
        val result = te > 7
        return result
    }
}