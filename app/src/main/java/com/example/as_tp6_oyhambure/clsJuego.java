package com.example.as_tp6_oyhambure;

import android.util.Log;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.RotateTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;

public class clsJuego {
    CCGLSurfaceView _VistaDelJuego;
    CCSize _Pantalla;
    Sprite _Jugador;
    Label _TituloJuego;
    Sprite _Enemigo;
    public clsJuego(CCGLSurfaceView VistaDelJuego){
        _VistaDelJuego = VistaDelJuego;
    }

    public void ComenzarJuego() {
        Director.sharedDirector().attachInView(_VistaDelJuego);
        _Pantalla=Director.sharedDirector().displaySize();
        Log.d("Juego", _Pantalla.getWidth() +" "+ _Pantalla.getHeight());
        Scene escenaAUsar;
        escenaAUsar=EscenaComienzo();

        Director.sharedDirector().runWithScene(escenaAUsar);
    }

    private Scene EscenaComienzo(){
        Scene escenaADevolver;
        escenaADevolver=Scene.node();

        capaJuego unacapa;
        unacapa = new capaJuego();
        escenaADevolver.addChild(unacapa);

        return escenaADevolver;
    }

    class capaJuego extends Layer {
        public capaJuego(){
           // ponerJugador();
            ponerFondo();
            ponertitulo();
           // ponerenemigo();
            super.schedule("ponerenemigo", 3.0f);
        }

        void ponerFondo(){
            Sprite imagenfondo;
            imagenfondo=Sprite.sprite("fondito.jpg");
            imagenfondo.setPosition(_Pantalla.getWidth()/2,_Pantalla.getHeight()/2);
            float factorAncho, factorAlto;
            factorAncho=_Pantalla.getWidth()/imagenfondo.getWidth();
            factorAlto=_Pantalla.getHeight()/imagenfondo.getHeight();
            imagenfondo.runAction(ScaleBy.action(0.01f,factorAncho,factorAlto));
            super.addChild(imagenfondo,-10);
        }

        void ponerJugador(){
            _Jugador=Sprite.sprite("avion.png");
            float posicionX, posicionY;
            posicionX = _Pantalla.getWidth()/2;
            posicionY = _Pantalla.getHeight()/2;
            _Jugador.setPosition(posicionX,120);
            super.addChild(_Jugador,10);
        }

        void ponertitulo(){
            _TituloJuego=Label.label("Super Juegazo","Verdana",70);

            _TituloJuego.setPosition(_Pantalla.getWidth()/2, _Pantalla.getHeight() - _TituloJuego.getHeight()/2);

            CCColor3B colortitulo;
            colortitulo = new CCColor3B(128,255,255);
            _TituloJuego.setColor(colortitulo);
            super.addChild(_TituloJuego, -5);
        }

        public void ponerenemigo(float diferenciadetiempo){
            _Enemigo=Sprite.sprite("epoch.gif");
            CCPoint posicionInicial;
            float alturaenemigo;
            alturaenemigo = _Enemigo.getHeight();
            posicionInicial = new CCPoint();
            Random generadorDeAzar= new Random();
            Random generadorDeAzarY = new Random();
            float anchoEnemigo, alturaEnemigo;
            anchoEnemigo = _Enemigo.getWidth();
            alturaEnemigo = _Enemigo.getHeight();
            posicionInicial.x = generadorDeAzar.nextInt((int) (_Pantalla.getWidth() - anchoEnemigo));
            posicionInicial.x += anchoEnemigo/2;
            posicionInicial.y = generadorDeAzarY.nextInt((int) (_Pantalla.getHeight() - alturaEnemigo));
            posicionInicial.y += alturaenemigo/2;
            _Enemigo.setPosition(posicionInicial.x,posicionInicial.y);
            _Enemigo.runAction(RotateTo.action(0.01f,180));
            CCPoint posicionFinal;
            posicionFinal = new CCPoint();
            float mitadpantalla = _Pantalla.getWidth()/2;
            float mitadpantallaalto = _Pantalla.getHeight()/2;
            if(posicionInicial.x < mitadpantalla)
            {
                posicionFinal.x = _Pantalla.getWidth(); //- anchoEnemigo/2;
            }
            else{
                posicionFinal.x = 0;// - anchoEnemigo/2;
            }
            if(posicionInicial.y < mitadpantallaalto)
            {
                posicionFinal.y = _Pantalla.getHeight();// - alturaEnemigo/2;
            }
            else
            {
                posicionFinal.y = 0;// - alturaenemigo/2;
            }

            _Enemigo.runAction(MoveTo.action(2,posicionFinal.x,posicionFinal.y));
            super.addChild(_Enemigo, 10);
        }
    }
}
