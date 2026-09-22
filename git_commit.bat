@echo off
chcp 65001 >nul
echo ========================================================
echo   RosKasa Android - Git Commit & Push na GitHub
echo ========================================================
echo.

git status -s
echo.
set /p commitMsg="Vnesite opis sprememb (ali pritisnite ENTER za privzeto): "

if "%commitMsg%"=="" (
    set commitMsg=Posodobitev projekta RosKasa
)

echo.
echo [1/3] Dodajanje sprememb (git add .)...
git add .

echo.
echo [2/3] Belezenje sprememb (git commit)...
git commit -m "%commitMsg%"

echo.
echo [3/3] Posiljanje na GitHub (git push origin master)...
git push origin master

if %ERRORLEVEL% equ 0 (
    echo.
    echo ========================================================
    echo   [USPEH] Spremembe so uspesno poslane na GitHub!
    echo ========================================================
) else (
    echo.
    echo ========================================================
    echo   [NAPAKA] Pri posiljanju je prislo do tezave.
    echo ========================================================
)

echo.
pause
