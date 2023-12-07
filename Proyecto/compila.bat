@Echo off
cls
del %1.al 2> null
java AnaLex %1
if errorlevel 1 goto Fallo
java aro_SLR %1
:Fallo