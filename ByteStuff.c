#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void main()
{
    char a[30], fs[50] = "", t[3], sd, ed, x[3], s[3], d[3], y[3];
    int i;
    printf("Enter characters to be stuffed: ");
    scanf("%s", a);
    printf("Enter starting delimeter: ");
    scanf(" %c", &sd);
    printf("Enter ending delimeter: ");
    scanf(" %c", &ed);
    x[0] = s[0] = s[1] = sd;
    x[1] = s[2] = '\0';
    y[0] = d[0] = d[1] = ed;
    d[2] = y[1] = '\0';
    strcat(fs, x);
    for (i = 0; i < strlen(a); i++)
    {
        t[0] = a[i];
        t[1] = '\0';
        if (t[0] == sd)
            strcat(fs, s);
        else if (t[0] == ed)
            strcat(fs, d);
        else
            strcat(fs, t);
    }
    strcat(fs, y);
    printf("\nAfter Stuffing\n %s\n", fs);
}
