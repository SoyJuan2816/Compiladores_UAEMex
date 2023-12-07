@Echo off
CLS

del %1.al 2> nul
java aro %1
if errorlevel 1 goto Fallo
echo ANALISIS LEXICOGRAFICO CORRECTO
goto FIN
:Fallo
echo ERRORES EN LA COMPILACION
:FIN
echo Compilacion Terminada