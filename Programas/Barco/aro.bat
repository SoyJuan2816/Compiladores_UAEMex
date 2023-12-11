@Echo off
CLS

del %1.al 2> nul
java aroAL %1
if errorlevel 1 goto Fallo
echo ANALISIS LEXICOGRAFICO CORRECTO

java aroSLR %1
if errorlevel 1 goto Fallo
echo ANALISIS SINTACTICO CORRECTO

goto FIN
:Fallo
echo ERRORES EN LA COMPILACION
:FIN
echo COMPILACION TERMINADA