@Echo off
CLS

del %1.bin 2> nul
del %1.sal 2> nul
del %1.asm 2> nul

java aro_AL %1
if errorlevel 1 goto Fallo
echo ANALISIS LEXICOGRAFICO CORRECTO

java aro_SLR %1
if errorlevel 1 goto Fallo
echo ANALISIS SINTACTICO CORRECTO
goto Fin

:Fallo
ECHO Compilacion terminada
del %1.sal 2> nul

:Fin
ECHO Proyecto correcto