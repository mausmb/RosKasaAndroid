@echo off
chcp 65001 >nul
echo ========================================================
echo   RosKasa Android - Git Pull (Posodobitev iz GitHub)
echo ========================================================
echo.

git pull origin master
if %ERRORLEVEL% equ 0 (
    echo.
    echo [USPEH] Repozitorij je uspesno posodobljen!
) else (
    echo.
    echo [NAPAKA] Pri posodobitvi je prislo do tezave.
)

echo.
pause
