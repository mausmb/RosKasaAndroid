## popust 99
1. Popust na celoten račun (placilo_id=99). V tem primeru se izvede popust na celoten znesek računa in zapiše placilo_id=99 in v racplaci.znesek je zapisan znesek popusta. racplaci.delni_znesek=0
2. VNOS PROCENTA POPUSTA. V racplaci.STATUS se zapiše procent . v RACPLACI.ZNESEK se vpiše vrednost izračunana iz procenta
3. VNOS ZNESEK POPSUTA. Iz zneska se izračuna procent popusta in v racplaci.STATUS se zapiše procent, v RACPLACI.ZNESEK se vpiše znesek.


## ZNESKOVNI popust na racpozic
1. Popust na vrstico pozicijaTP (vpiše se v racpozic.ZNESEK_POPUST)

## LOJALNOSTNI  popust na racpozic
1. Popust na vrstico pozicijaTP (racpozic.LOJALNOST_POPUST = procent popusta in racpozic.ZNESEK_LOJALNOST = znesek popusta)

## posebna plačila, ki zahtevajo dodaten vnos ali dodatne operacije
1. dobavnica
2. hotel kredit
3. POS plačilo
4. darilni boni
5. boni in kuponi