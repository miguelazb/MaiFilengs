package zamorano.miguel.missentimientos.utilities

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import zamorano.miguel.missentimientos.R

class CustomBarDrawable: Drawable {
    var coordenadas: RectF? = null;
    var context: Context? = null;
    var emocion: Emociones? = null;

    constructor(context: Context, emocion: Emociones){
        this.context = context;
        this.emocion = emocion;


    }

    override fun draw(canvas: Canvas) {

        val fondo = Paint();
        fondo.style = Paint.Style.FILL;
        fondo.isAntiAlias = true;
        fondo.color = context?.resources?.getColor(R.color.gris) ?: R.color.gris;
        val ancho: Float = (canvas.width - 10).toFloat();
        val alto: Float = (canvas.height - 10).toFloat();

        coordenadas = RectF(0.0f, 0.0f, ancho, alto);

        canvas.drawRect(coordenadas!!, fondo);

        if(this.emocion != null) {

            val porcentaje: Float = this.emocion!!.porcentaje * (canvas.width - 10) / 100.0f;
            var coordenadas2 = RectF(0.0f, 0.0f, porcentaje, alto);

            val seccion = Paint();
            seccion.style = Paint.Style.FILL;
            seccion.isAntiAlias = true;
            seccion.color = ContextCompat.getColor(this.context!!, emocion!!.color)

            canvas.drawRect(coordenadas2!!, seccion);
        }


    }

    override fun setAlpha(alpha: Int) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }
}