package zamorano.miguel.missentimientos.utilities

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import zamorano.miguel.missentimientos.R

class CustomCircleDrawable: Drawable {
    var coordenadas: RectF? = null;
    var anguloBarrido: Float = 0.0F;
    var anguloInicio: Float = 0.0F;
    var grosorMetrica: Int = 0;
    var grosorFondo: Int = 0;
    var context: Context? = null;
    var emociones = ArrayList<Emociones>();

    constructor(context: Context, emociones: ArrayList<Emociones>){
        this.context = context;
        this.emociones = emociones;
        grosorMetrica = context.resources.getDimensionPixelSize(R.dimen.graphWith);
        grosorFondo = context.resources.getDimensionPixelSize(R.dimen.graphBackground);
    }

    override fun draw(canvas: Canvas) {
        val fondo = Paint();
        fondo.style = Paint.Style.STROKE;
        fondo.strokeWidth = (this.grosorFondo).toFloat();
        fondo.isAntiAlias = true;
        fondo.strokeCap = Paint.Cap.ROUND;
        fondo.color = context?.resources?.getColor(R.color.gris) ?: R.color.gris;
        val ancho: Float = (canvas.width - 25).toFloat();
        val alto: Float = (canvas.height - 25).toFloat();
        coordenadas = RectF(25.0f, 25.0f, ancho, alto);
        canvas.drawArc(coordenadas!!, 0.0F, 360.0F, false, fondo);

        if(emociones.size != 0) {
            for(e in emociones){
                val grado: Float = (e.porcentaje*360)/100.0f;
                this.anguloBarrido = grado;
                val fondo2 = Paint();
                fondo2.style = Paint.Style.STROKE;
                fondo2.strokeWidth = (this.grosorFondo).toFloat();
                fondo2.isAntiAlias = true;
                fondo2.strokeCap = Paint.Cap.ROUND;
                fondo2.color = ContextCompat.getColor(this.context!!, e.color)
                val ancho: Float = (canvas.width - 25).toFloat();
                val alto: Float = (canvas.height - 25).toFloat();
                coordenadas = RectF(25.0f, 25.0f, ancho, alto);
                canvas.drawArc(coordenadas!!, this.anguloInicio, this.anguloBarrido, false, fondo2);
                this.anguloInicio += this.anguloBarrido;
            }
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