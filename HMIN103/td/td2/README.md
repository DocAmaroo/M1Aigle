```xml
<tweet_data>
    <tweet id="1" os="computer" langage="en">
        <profile idUser="u63541351">
            <pseudo>MTV Music</pseudo>
            <ref_user color="gray">@MTVMusicUK</ref_user>
            <description>"j'adore la brandade de morue !"</description>
            <pp>"https://myprofilepic.jpg"</pp>
            <followers>
                <!-- une liste de follow -->
            </followers>
            <follow>
                <!-- une liste de follow -->
            </follow>
        </profile>

        <date>
            <day>"18"</day>
            <month>"09"</month>
            <year>"2020"</year>
            <hour>"00"</hour>
            <minute>"00"</minute>
            <second>"00"</second>
        </date>

        <location>
            <longitude>"unknow"</longitude>
            <latitude>"unknow"</latitude>
            <city>"unknow"</city>
            <country>"unknow"</country>
        </location>

        <corps>
            <text>.</text>
            <ref_user>@example</ref_user>
            <text>absolutely smashed it at</text>
            <hashtag>#mtvlivelockdown</hashtag>
            <text>! Catch him at the official</text>
            <ref_user>@clubmtvuk</ref_user>
            <text>after party tonight @ 10pm</text>
        </corps>

    </tweet>
</tweet_data>
```

Exercice 2 :

Différence constatées :
- Pour le corps plutôt que de séparer le corps en txt puis hashtags puis references, faire en sorte que le corps soit (txt|hashtags|references)*
- Au lieu de mettre la langue directement sur le tweet, il faudrait mieux l'associer au corps d'un message (<corps>)
- ajout de balise follow et follower pour créer une liste de follows et de followers, faisant référence à une IDREF

Version final :
```xml
<tweet_data>
    <tweet id="1" os="computer">
        <profile idUser="u63541351">
            <pseudo>MTV Music</pseudo>
            <ref_user type="" size="" color="gray">@MTVMusicUK</ref_user>
            <description>"j'adore la brandade de morue !"</description>
            <pp>"https://myprofilepic.jpg"</pp>
            <followers>
                <follower idUser="u55458"/>
                <follower idUser="u54934"/>
            </followers>
            <follow>
                <follow idUser="u68468"/>
                <follow idUser="u4686"/>
            </follow>
        </profile>

        <date>
            <day>"18"</day>
            <month>"09"</month>
            <year>"2020"</year>
            <hour>"00"</hour>
            <minute>"00"</minute>
            <second>"00"</second>
        </date>

        <location>
            <longitude>"unknow"</longitude>
            <latitude>"unknow"</latitude>
            <city>"unknow"</city>
            <country>"unknow"</country>
        </location>

        <corps>
            <text>.</text>
            <ref_user>@example</ref_user>
            <text>absolutely smashed it at</text>
            <hashtag>#mtvlivelockdown</hashtag>
            <text>! Catch him at the official</text>
            <ref_user>@clubmtvuk</ref_user>
            <text>after party tonight @ 10pm</text>
        </corps>

    </tweet>
</tweet_data>
```

Exercice 3 : 
/ à refaire

