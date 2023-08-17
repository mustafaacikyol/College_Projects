#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <string.h>
#include <stdbool.h>

char klasorAdlari[50][50];
int indexKlasorAdlari = 0;
char txtDosyalari[1000][100];
int indexTxtDosyalari = 0;
char etiketler[1000][1000];
int indexEtiketler;
char yetimEtiketler[100][100];
int indexYetimEtiketler = 0;
char istenenEtiketler[100][100];
int indexIstenenEtiketler = 0;
bool birinci = false;
bool ikinci = false;
bool ucuncu = false;
bool dorduncu = false;
int tekrarSayisi = 1;
char arananKelime[200];
char guncellenecekEtiket[100];
bool etiketVarMi;
char yeniEtiket[100];
char dosyaninKalani[10000];
int indexDosyaninKalani = 0;
char etiketler2[100][100];
int indexEtiketler2 = 0;
char yetimEtiketler2[500][100];
int indexYetimEtiketler2 = 0;
int dersKodu = 200;

void yetimEtiketleriBulma()
{
    bool etiketYetimmi;
    bool onceYazilmismi;
    for (int i = 0; i < indexEtiketler; i++)
    {
        etiketYetimmi = true;
        onceYazilmismi = false;
        for (int j = 0; j < indexTxtDosyalari; j++)
        {
            if (strcmp(etiketler[i], txtDosyalari[j]) == 0)
            {
                etiketYetimmi = false;
                break;
            }
        }

        if (etiketYetimmi)
        {

            for (int k = 0; k < indexYetimEtiketler; k++)
            {
                if (strcmp(etiketler[i], yetimEtiketler[k]) == 0)
                {
                    onceYazilmismi = true;
                }
            }

            if (!onceYazilmismi)
            {
                strcpy(yetimEtiketler[indexYetimEtiketler], etiketler[i]);
                indexYetimEtiketler++;
            }
        }
    }
}

void istenenEtiketleriBulma()
{

    bool etiketIstenenmi;
    bool onceYazilmismi;

    for (int i = 0; i < indexTxtDosyalari; i++)
    {
        etiketIstenenmi = true;
        onceYazilmismi = false;
        for (int j = 0; j < indexEtiketler; j++)
        {
            if (strcmp(txtDosyalari[i], etiketler[j]) == 0)
            {
                etiketIstenenmi = false;
                break;
            }
        }

        if (etiketIstenenmi)
        {

            for (int k = 0; k < indexIstenenEtiketler; k++)
            {
                if (strcmp(txtDosyalari[i], etiketler[k]) == 0)
                {
                    onceYazilmismi = true;
                }
            }

            if (!onceYazilmismi)
            {
                strcpy(istenenEtiketler[indexIstenenEtiketler], txtDosyalari[i]);
                indexIstenenEtiketler++;
            }
        }
    }
}

void outputDosyasinaYazma()
{
    bool onceYazilmismi = false;
    FILE *output = fopen("output.txt", "w");
    if (output == NULL)
    {
        printf("dosya acilamadi\n");
        exit(1);
    }

    fputs("Etiket Listesi\t\t\t\t\t", output);
    fputs("Tekrar Sayısı\n", output);

    for (int i = 0; i < indexEtiketler; i++)
    {
        for (int j = 0; j <= indexEtiketler; j++)
        {
            if (strcmp(etiketler[i], etiketler[j]) == 0 && i > j)
            {
                onceYazilmismi = true;
                break;
            }
            else if (strcmp(etiketler[i], etiketler[j]) == 0 && i != j)
            {
                tekrarSayisi++;
            }
        }

        if (!onceYazilmismi)
        {
            fprintf(output, "%-55s", etiketler[i]);
            fprintf(output, "%d\n", tekrarSayisi);
            tekrarSayisi = 1;
        }
        else
        {
            onceYazilmismi = false;
        }
    }

    fputs("\n\nYetim Etiketler\n", output);

    for (int i = 0; i < indexYetimEtiketler; i++)
    {
        bool artikYetimMi = true;
        for (int j = 0; j < indexYetimEtiketler2; j++)
        {
            if (strcmp(yetimEtiketler[i], yetimEtiketler2[j]) == 0)
            {
                artikYetimMi = false;
                break;
            }
        }

        if (artikYetimMi)
        {
            fprintf(output, "%s\n", yetimEtiketler[i]);
        }
    }

    fclose(output);
}

void klasordenOkuma(char klasorAdi[100], char *islemTuru)
{
    // printf("%s\n", klasorAdi);
    char klasor[1000][100];
    char klasorYolu[1000];

    struct dirent *de;
    DIR *folder;
    if (strcmp(klasorAdi, "../Üniversite/") == 0)
    {
        strcpy(klasorYolu, "../Üniversite/");
    }
    else
    {
        strcpy(klasorYolu, "../Üniversite/");
        strcat(klasorYolu, klasorAdi);
        strcat(klasorYolu, "/");
    }

    // printf("%s\n", klasorYolu);
    folder = opendir(klasorYolu);
    if (folder == NULL)
    {
        printf("Dizin Okunamadı!\n");
        exit(1);
    }
    int a;
    for (a = 0; (de = readdir(folder)) != NULL; a++)
    {
        strcpy(klasor[a], de->d_name);
    }
    for (int j = 2; j < a; j++)
    {
        // printf("%s\n", klasor[j]);
        char *dosyami = strchr(klasor[j], '.');
        char *uzanti = strtok(klasor[j], ".");
        uzanti = strtok(NULL, ".");
        // printf("%s\n", uzanti);

        if (dosyami == NULL)
        {
            if (strcmp(islemTuru, "okuma") == 0)
            {
                strcpy(klasorAdlari[indexKlasorAdlari], klasor[j]);
                indexKlasorAdlari++;
                // klasordenOkuma(klasor[j]);
            }
        }
        else if (dosyami != NULL && strcmp(uzanti, "txt") == 0)
        {
            if (strcmp(islemTuru, "okuma") == 0)
            {
                strcpy(txtDosyalari[indexTxtDosyalari], klasor[j]);
                indexTxtDosyalari++;
            }
            // printf("%s", txtDosyalari[0]);
            strcat(klasor[j], ".");
            strcat(klasor[j], "txt");
            // printf("%s\n", klasor[j]);

            char dosyaYolu[1000];
            strcpy(dosyaYolu, klasorYolu);

            strcat(dosyaYolu, klasor[j]);
            // printf("%s\n", dosyaYolu);
            FILE *dosya = fopen(dosyaYolu, "r+");
            if (dosya == NULL)
            {
                printf("dosya acilamadi\n");
                exit(1);
            }
            char *dosyaAdi;
            if (strcmp(islemTuru, "guncelleme") == 0)
            {
                dosyaAdi = strtok(klasor[j], ".");
                if (strcmp(dosyaAdi, guncellenecekEtiket) == 0)
                {
                    char klasorYolu2[1000];
                    strcpy(klasorYolu2, klasorYolu);
                    strcat(yeniEtiket, ".");
                    strcat(yeniEtiket, "txt");
                    strcat(klasorYolu2, yeniEtiket);
                    rename(dosyaYolu, klasorYolu2);
                }
            }

            fseek(dosya, 0, SEEK_SET);
            if (strcmp(islemTuru, "okuma") == 0 || strcmp(islemTuru, "guncelleme") == 0)
            {
                char c;
                int parantezNo;
                while ((c = fgetc(dosya)) != EOF)
                {
                    // printf("karakter: %c (%d)\n", c, c);

                    int ilkKarakter;
                    int sonKarakter;
                    int birinciParantez;
                    int ikinciParantez;
                    int ucuncuParantez;
                    int dorduncuParantez;

                    parantezNo = ftell(dosya);

                    if (c == '[' && !birinci && !ikinci && !ucuncu && !dorduncu)
                    {
                        birinci = true;
                        birinciParantez = ftell(dosya);
                    }
                    else if (c == '[' && parantezNo == birinciParantez + 1 && birinci && !ikinci && !ucuncu && !dorduncu)
                    {
                        ikinci = true;
                        ikinciParantez = parantezNo;
                        ilkKarakter = parantezNo;
                        // printf("%d\n", ilkKarakter);
                    }
                    else if (c == ']' && birinci && ikinci && !ucuncu && !dorduncu)
                    {
                        ucuncu = true;
                        ucuncuParantez = ftell(dosya);
                    }
                    else if (c == ']' && parantezNo == ucuncuParantez + 1 && birinci && ikinci && ucuncu && !dorduncu)
                    {
                        dorduncu = true;
                        sonKarakter = ftell(dosya) - 2;
                        // printf("%d\n", sonKarakter);
                    }

                    if (parantezNo > birinciParantez + 1 && birinci && !ikinci)
                    {
                        birinci = false;
                    }

                    if (parantezNo > ucuncuParantez + 1 && birinci && ikinci & ucuncu & !dorduncu)
                    {
                        birinci = false;
                        ikinci = false;
                        ucuncu = false;
                    }

                    if (birinci && ikinci && ucuncu && dorduncu)
                    {
                        bool etiketIkiMi = false;
                        fseek(dosya, ilkKarakter, SEEK_SET);
                        for (int i = 0; i < sonKarakter - ilkKarakter; i++)
                        {
                            c = fgetc(dosya);
                            etiketler[indexEtiketler][i] = c;
                            // printf("%c", etiketler[indexEtiketler][i]);
                        }
                        if (strcmp(klasorYolu, "../Üniversite/Bölümler/") == 0)
                        {
                            strcpy(etiketler2[indexEtiketler2], etiketler[indexEtiketler]);
                            etiketIkiMi = true;
                        }
                        // printf("%s\n", etiketler[indexEtiketler]);
                        if (strcmp(islemTuru, "guncelleme") == 0)
                        {
                            if (strcmp(guncellenecekEtiket, etiketler[indexEtiketler]) == 0)
                            {
                                // printf("%d\n", 17);
                                fseek(dosya, sonKarakter, SEEK_SET);
                                int sayac = 0;
                                while ((c = fgetc(dosya)) != EOF)
                                {
                                    dosyaninKalani[indexDosyaninKalani] = c;
                                    indexDosyaninKalani++;
                                    sayac++;
                                }

                                fseek(dosya, ilkKarakter, SEEK_SET);
                                for (int i = 0; i < strlen(yeniEtiket); i++)
                                {
                                    fprintf(dosya, "%c", yeniEtiket[i]);
                                }

                                fprintf(dosya, "%s", dosyaninKalani);

                                if (strlen(yeniEtiket) < strlen(guncellenecekEtiket))
                                {
                                    for (int i = 0; i < strlen(guncellenecekEtiket) - strlen(yeniEtiket); i++)
                                    {
                                        fprintf(dosya, "%c", ' ');
                                    }
                                }

                                strcpy(etiketler[indexEtiketler], yeniEtiket);
                                printf("Etiket güncellendi.\n");
                                printf("Dosya adı güncellendi.\n");
                            }
                        }

                        indexEtiketler++;

                        if (etiketIkiMi)
                        {
                            indexEtiketler2++;
                        }

                        birinci = false;
                        ikinci = false;
                        ucuncu = false;
                        dorduncu = false;
                    }
                }
            }

            else if (strcmp(islemTuru, "arama") == 0)
            {
                int dosyadakiKelimeUzunlugu;
                int arananKelimeUzunlugu;
                bool kelimeVarmi;
                bool etiketMi;
                bool etiketBaslangici = false;
                bool etiketSonu = false;
                char etiketKelimeleri[100][50];
                int indexEtiketKelimeleri = 0;
                bool etiketIcindeKelimeVarmi;
                int satirSayisi = 1;
                char dosyadakiSatir[5000];
                bool karakterSayisiEsitMi;
                char ayrac[] = " \t\r\n\v\f@.,:;";
                arananKelimeUzunlugu = strlen(arananKelime);

                while (fgets(dosyadakiSatir, 5000, dosya) != NULL)
                {
                    char *kelime = strtok(dosyadakiSatir, ayrac);
                    while (kelime != NULL)
                    {
                        // printf("%s\n", kelime);
                        etiketMi = false;
                        kelimeVarmi = true;
                        etiketIcindeKelimeVarmi = true;
                        karakterSayisiEsitMi = false; // Kelime uzunluğu
                        dosyadakiKelimeUzunlugu = strlen(kelime);

                        if (kelime[0] == '[' && kelime[1] == '[' && kelime[dosyadakiKelimeUzunlugu - 2] == ']' && kelime[dosyadakiKelimeUzunlugu - 1] == ']')
                        {
                            etiketMi = true;
                        }

                        if (etiketBaslangici && !etiketSonu)
                        {
                            strcpy(etiketKelimeleri[indexEtiketKelimeleri], kelime);
                            indexEtiketKelimeleri++;
                        }

                        if (kelime[0] == '[' && kelime[1] == '[' && !etiketMi)
                        {
                            etiketBaslangici = true;
                            strcpy(etiketKelimeleri[indexEtiketKelimeleri], kelime);
                            indexEtiketKelimeleri++;
                        }

                        if (kelime[dosyadakiKelimeUzunlugu - 2] == ']' && kelime[dosyadakiKelimeUzunlugu - 1] == ']' && !etiketMi && etiketBaslangici)
                        {
                            etiketSonu = true;
                        }

                        if (etiketMi)
                        {
                            int j = 2;
                            for (int i = 0; i < arananKelimeUzunlugu; i++)
                            {
                                if (kelime[j] != arananKelime[i])
                                {
                                    kelimeVarmi = false;
                                    break;
                                }
                                j++;
                                // Kelime uzunluğu
                                if (arananKelimeUzunlugu == dosyadakiKelimeUzunlugu - 4)
                                {
                                    karakterSayisiEsitMi = true;
                                }
                                // Kelime uzunluğu
                            }
                        }
                        else
                        {
                            for (int i = 0; i < arananKelimeUzunlugu; i++)
                            {
                                if (kelime[i] != arananKelime[i])
                                {
                                    kelimeVarmi = false;
                                    break;
                                }
                                // Kelime uzunluğu
                                if (arananKelimeUzunlugu == dosyadakiKelimeUzunlugu)
                                {
                                    karakterSayisiEsitMi = true;
                                }
                                // Kelime uzunluğu
                            }
                        }

                        if (kelimeVarmi && !etiketBaslangici && karakterSayisiEsitMi) // Kelime uzunluğu
                        {

                            printf("%s\t\t\t", arananKelime);
                            if (etiketMi)
                            {
                                printf("%s\t\t\t\t", "Etiket");
                            }
                            else
                            {
                                printf("%s\t\t\t\t", "Değil");
                            }

                            printf("%-30s\t\t\t\t\t", klasor[j]);
                            printf("%5d\n", satirSayisi);
                        }

                        if (etiketBaslangici && etiketSonu)
                        {
                            int l;
                            for (int i = 0; i < indexEtiketKelimeleri; i++)
                            {

                                if (i == 0)
                                {
                                    l = 2;
                                }
                                else
                                {
                                    l = 0;
                                }

                                etiketIcindeKelimeVarmi = true;

                                for (int k = 0; k < arananKelimeUzunlugu; k++)
                                {
                                    if (etiketKelimeleri[i][l] != arananKelime[k])
                                    {
                                        etiketIcindeKelimeVarmi = false;
                                        break;
                                    }
                                    l++;
                                }

                                if (etiketIcindeKelimeVarmi)
                                {
                                    // Kelime uzunluğu
                                    if (i == 0 || i == indexEtiketKelimeleri - 1)
                                    {
                                        if (arananKelimeUzunlugu == dosyadakiKelimeUzunlugu - 2)
                                        {
                                            karakterSayisiEsitMi = true;
                                        }
                                    }
                                    else
                                    {
                                        if (arananKelimeUzunlugu == dosyadakiKelimeUzunlugu)
                                        {
                                            karakterSayisiEsitMi = true;
                                        }
                                    }
                                    // Kelime uzunluğu

                                    printf("%s\t\t", arananKelime);
                                    printf("%s\t\t\t", "Etiket");
                                    printf("%-20s\t\t", klasor[j]);
                                    printf("%d\n", satirSayisi);
                                    break;
                                }
                            }

                            etiketBaslangici = false;
                            etiketSonu = false;
                            indexEtiketKelimeleri = 0;
                        }

                        kelime = strtok(NULL, ayrac);
                    }
                    satirSayisi++;
                }
            }

            fclose(dosya);
        }
    }

    closedir(folder);
}

void arama()
{
    int satirSayaci = 1;
    printf("Aranacak kelimeyi giriniz : ");
    scanf("%s", arananKelime);
    // printf("%s\n", arananKelime);

    printf("\nYetim Etiketler\n");

    for (int i = 0; i < indexYetimEtiketler; i++)
    {
        printf("%s\n", yetimEtiketler[i]);
    }

    printf("\n\nİstenen Etiketler\n");

    for (int i = 0; i < indexIstenenEtiketler; i++)
    {
        printf("%s\n",istenenEtiketler[i]);
    }

    printf("\n\nArama Sonucu\n\nKelime\t\t\t\tTürü\t\t\t\t\tDosya Adı\t\t\t\t\t\tSatır No\n");

    klasordenOkuma("../Üniversite/", "arama");

    for (int i = 0; i < indexKlasorAdlari; i++)
    {
        // printf("%s\n", klasorAdlari[i]);
        klasordenOkuma(klasorAdlari[i], "arama");
    }
}

void guncelleme()
{
    // while (getchar() != '\n');
    etiketVarMi = false;
    int c;
    while (!etiketVarMi)
    {
        while(getchar() != '\n');
        printf("Güncellemek istediğiniz etiketi giriniz : ");
        fgets(guncellenecekEtiket, 100, stdin);
        guncellenecekEtiket[strcspn(guncellenecekEtiket, "\n")] = 0;
        for (c = 0; c < indexEtiketler; c++)
        {
            if (strcmp(guncellenecekEtiket, etiketler[c]) == 0)
            {
                printf("Yeni etiketin adını giriniz : ");
                fgets(yeniEtiket, 100, stdin);
                yeniEtiket[strcspn(yeniEtiket, "\n")] = 0;
                etiketVarMi = true;
                break;
            }
        }

        if (!etiketVarMi)
        {
            printf("Bu isimde bir etiket BULUNAMADI!\n");
        }
    }

    indexEtiketler = 0;
    klasordenOkuma("../Üniversite/", "guncelleme");

    for (int i = 0; i < indexKlasorAdlari; i++)
    {
        // printf("%s\n", klasorAdlari[i]);
        klasordenOkuma(klasorAdlari[i], "guncelleme");
    }
}

void yetimEtiketlerinTxtDosyasiniOlusturma()
{

    bool etiketYetimmi;
    bool onceYazilmismi;
    for (int i = 0; i < indexEtiketler2; i++)
    {
        etiketYetimmi = true;
        onceYazilmismi = false;
        for (int j = 0; j < indexTxtDosyalari; j++)
        {
            if (strcmp(etiketler2[i], txtDosyalari[j]) == 0)
            {
                etiketYetimmi = false;
                break;
            }
        }

        if (etiketYetimmi)
        {

            for (int k = 0; k < indexYetimEtiketler2; k++)
            {
                if (strcmp(etiketler2[i], yetimEtiketler2[k]) == 0)
                {
                    onceYazilmismi = true;
                }
            }

            if (!onceYazilmismi)
            {
                strcpy(yetimEtiketler2[indexYetimEtiketler2], etiketler2[i]);
                indexYetimEtiketler2++;
            }
        }
    }

    for (int i = 0; i < indexYetimEtiketler2; i++)
    {
        char yeniDosyaYolu[100] = "../Üniversite/Dersler/";
        strcat(yeniDosyaYolu, yetimEtiketler2[i]);
        strcat(yeniDosyaYolu, ".txt");
        FILE *dosya = fopen(yeniDosyaYolu, "w");
        if (dosya == NULL)
        {
            printf("dosya acilamadi\n");
            exit(1);
        }

        fprintf(dosya, "%s\t%d\n\n", "Dersin Kodu : ", dersKodu);
        fprintf(dosya, "%s\t%s\n\n", "Dersin Adı : ", yetimEtiketler2[i]);
        fprintf(dosya, "%s", "Dersin İçeriği : ");
        dersKodu++;
    }

    outputDosyasinaYazma();
    printf("Yetim etiketlerin txt dosyaları oluşturuldu.\n");
}

int main()
{
    int islem;
    printf("Menü\n");
    printf("0-Programı Sonlandır\n");
    printf("1-Programı Çalıştır\n");
    printf("2-Arama\n");
    printf("3-Güncelleme\n");
    printf("4-Yetim Etiketlerin Txt Dosyasını Oluşturma\n");

    while (islem != 0)
    {
       
        printf("Yapmak istediğiniz işlemi seçiniz : ");
        scanf("%d", &islem);

        if (islem == 1)
        {
            klasordenOkuma("../Üniversite/", "okuma");

            for (int i = 0; i < indexKlasorAdlari; i++)
            {
                klasordenOkuma(klasorAdlari[i], "okuma");
            }

            yetimEtiketleriBulma();
            istenenEtiketleriBulma();
            outputDosyasinaYazma();
            printf("Program çalıştırıldı.\n");
             
        }
        else if (islem == 2)
        {
            arama();
        }
        else if (islem == 3)
        {
            guncelleme();
            
        }
        else if (islem == 4)
        {
            yetimEtiketlerinTxtDosyasiniOlusturma();
        }
    }
    
    printf("Program sonlandırıldı.\n");

    return 0;
}
