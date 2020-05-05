package zamorano.miguel.missentimientos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import zamorano.miguel.missentimientos.utilities.CustomBarDrawable
import zamorano.miguel.missentimientos.utilities.CustomCircleDrawable
import zamorano.miguel.missentimientos.utilities.Emociones
import zamorano.miguel.missentimientos.utilities.JSONFile


class MainActivity : AppCompatActivity() {

    var jsonFile: JSONFile? = null
    var muyFeliz= 0.0f
    var feliz = 0.0f
    var neutro = 0.0f
    var triste= 0.0f
    var muyTriste = 0.0f
    var data: Boolean = false
    var lista = ArrayList<Emociones>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jsonFile = JSONFile()

        fetchingData()
        if(!data) {
            var emociones = ArrayList<Emociones>()
            val fondo = CustomCircleDrawable(this, emociones)
            grafica.background = fondo;
            graficaMuyFeliz.background = CustomBarDrawable(this, Emociones("Muy feliz", 0.0F, R.color.mostaza, muyFeliz))
            graficaFeliz.background = CustomBarDrawable(this, Emociones("Feliz", 0.0F, R.color.naranja, muyFeliz))
            graficaNeutro.background = CustomBarDrawable(this, Emociones("Neutro", 0.0F, R.color.verdesillo, muyFeliz))
            graficaTriste.background = CustomBarDrawable(this, Emociones("Triste", 0.0F, R.color.azul, muyFeliz))
            graficaMuyTriste.background = CustomBarDrawable(this, Emociones("Muy triste", 0.0F, R.color.azulMarino, muyFeliz))
        } else {
            actualizarGrafica()
            iconoMayoria()
        }

        botonGuardar.setOnClickListener{
            guardar()
        }

        botonMuyFeliz.setOnClickListener {
            muyFeliz++
            iconoMayoria()
            actualizarGrafica()
        }
        botonFeliz.setOnClickListener {
            feliz++
            iconoMayoria()
            actualizarGrafica()
        }
        botonNeutro.setOnClickListener {
            neutro++
            iconoMayoria()
            actualizarGrafica()
        }
        botonTriste.setOnClickListener {
            triste++
            iconoMayoria()
            actualizarGrafica()
        }
        botonMuyTriste.setOnClickListener {
            muyTriste++
            iconoMayoria()
            actualizarGrafica()
        }

    }

    fun fetchingData(){
        try {
            var json: String = jsonFile?.getData(this) ?: ""
            if(json != ""){
                this.data = true
                var jsonArray = JSONArray(json)
                this.lista = parseJson(jsonArray)


                for (i in lista) {
                    when(i.nombre){
                        "Muy feliz" -> muyFeliz = i.total
                        "Feliz" -> feliz = i.total
                        "Neutro" -> neutro = i.total
                        "Triste" -> triste= i.total
                        "Muy triste" -> muyTriste = i.total
                    }
                }

            } else {
                this.data = false
            }
        } catch(e: JSONException) {
            e.printStackTrace()
        }
    }

    fun parseJson (json: JSONArray):  ArrayList<Emociones>{
        var lista = ArrayList<Emociones>()
        for(i in 0..json.length()){
            try {
                val nombre = json.getJSONObject(i).getString("nombre")
                val porcentaje = json.getJSONObject(i).getString("porcentaje").toFloat()
                val color = json.getJSONObject(i).getInt("color")
                val total= json.getJSONObject(i).getString("total").toFloat()
                var emocion = Emociones(nombre, porcentaje, color, total)
                lista.add(emocion)

            } catch (e: JSONException){
                e.printStackTrace()
            }
        }
        return lista
    }
    fun actualizarGrafica () {
        var total: Float = muyFeliz + feliz + neutro + triste + muyTriste

        var pMF: Float = (muyFeliz*100f / total)
        var pF: Float = (feliz*100f / total)
        var pN: Float = (neutro*100f / total)
        var pT: Float = (triste*100f / total)
        var pMT: Float = (muyTriste*100f / total)

        Log.d("porcentajes", "Muy feliz" + pMF)
        Log.d("porcentajes", "Feliz" + pF)
        Log.d("porcentajes", "Neutro" + pN)
        Log.d("porcentajes", "Triste" + pT)
        Log.d("porcentajes", "Muy triste" + pMT)

        lista.clear()
        lista.add(Emociones("Muy feliz", pMF, R.color.mostaza, muyFeliz))
        lista.add(Emociones("Feliz", pF, R.color.naranja, feliz))
        lista.add(Emociones("Neutro", pN, R.color.verdesillo, neutro))
        lista.add(Emociones("Triste", pT, R.color.azul, triste))
        lista.add(Emociones("Muy triste", pMT, R.color.azulMarino, muyTriste))

        val fondo = CustomCircleDrawable(this, lista)

        graficaMuyFeliz.background = CustomBarDrawable(this, Emociones("Muy feliz", pMF, R.color.mostaza, muyFeliz))
        graficaFeliz.background = CustomBarDrawable(this, Emociones("Feliz", pF, R.color.naranja, feliz))
        graficaNeutro.background = CustomBarDrawable(this, Emociones("Neutro", pN, R.color.verdesillo, neutro))
        graficaTriste.background = CustomBarDrawable(this, Emociones("Triste", pT, R.color.azul, triste))
        graficaMuyTriste.background = CustomBarDrawable(this, Emociones("Muy triste", pMT, R.color.azulMarino, muyTriste))

        grafica.background = fondo
    }

    fun iconoMayoria() {
        if(muyFeliz>feliz && muyFeliz>neutro && muyFeliz>triste && muyFeliz>muyTriste) {
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_sentiment_very_satisfied_black_24dp))
        }
        if(feliz>muyFeliz && feliz>neutro && feliz>triste && feliz>muyTriste) {
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_sentiment_satisfied_black_24dp))
        }
        if(neutro>feliz && neutro>muyFeliz && neutro>triste && neutro>muyTriste) {
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_sentiment_neutral_black_24dp))
        }
        if(triste>feliz && triste>neutro && triste>muyFeliz && triste>muyTriste) {
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_sentiment_dissatisfied_black_24dp))
        }
        if(muyTriste>feliz && muyTriste>neutro && muyTriste>triste && muyTriste>muyFeliz) {
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_sentiment_very_dissatisfied_black_24dp))
        }
    }

    fun guardar(){
        var json = JSONArray()
        var o: Int = 0
        for(i in lista){
            Log.d("objetos", i.toString())
            var j = JSONObject()
            j.put("nombre", i.nombre)
            j.put("porcentaje", i.porcentaje)
            j.put("color", i.color)
            j.put("total", i.total)

            json.put(o, j)
            o++;
        }

        jsonFile?.saveData(this, json.toString())
        Toast.makeText(this,"Datos guardados", Toast.LENGTH_SHORT).show()

    }

}
