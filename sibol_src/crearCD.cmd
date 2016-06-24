@echo off

echo Creando estructura de directorios para copiar a CD...
echo.

set rutaSIBOL=C:\Datos\Proyectos\SIBOL
set rutaCD=%rutaSIBOL%\CD

rd /s /q %rutaCD%
md %rutaCD%

copy %rutaSIBOL%\autorun.inf %rutaCD%
copy %rutaSIBOL%\sibol.ico %rutaCD%

copy %rutaSIBOL%\instalar_SIBOL.exe %rutaCD%
copy %rutaSIBOL%\instalar_SIBOL.jar %rutaCD%
md %rutaCD%\jre
xcopy /e %rutaSIBOL%\jre\*.* %rutaCD%\jre

echo.
echo Estructura de directorios creada en %rutaCD%
echo.
pause

set rutaSIBOL=
set rutaCD=