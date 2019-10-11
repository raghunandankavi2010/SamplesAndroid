package com.demo.sticker;

import java.util.ArrayList;
import java.util.List;

public class StickersDataFactory {

  public static List<Sticker> getAllStickerReference() {
    String[] stickerURLRef = {
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/energy_is_everything.webp?alt=media&token=f426c7f9-3347-41d1-917d-bbea10ed174a",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/blessings.webp?alt=media&token=d6046e5f-57d0-4912-a749-c0914e3042f8",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/feel_good_1.webp?alt=media&token=f8aa58e5-cd8c-4912-a6be-adc04b45a7ac",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/fist-style-1.webp?alt=media&token=a8c6cec6-d4be-43be-82fe-2fc13a363d9d",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/GOOD-VIBE-512_01.webp?alt=media&token=7144a670-c5b8-4886-a378-a0d104f787c7",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/GOOD-VIBE-512_02.webp?alt=media&token=eb490808-9858-40c3-9162-86c0b7da8d6b",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/GOOD-VIBE-512_03.webp?alt=media&token=0c380297-53ca-42af-97ed-aed103496b64",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/GOOD-VIBE-512_04.webp?alt=media&token=c2f78353-6891-4ceb-905f-68f7791bf1b9",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/GOOD-VIBE-512_05.webp?alt=media&token=58089d60-a801-4640-b658-589363fb347a",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/GOOD-VIBE-512_06.webp?alt=media&token=a004e38d-43c3-43d5-9bd0-c2f427e29847",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/GOOD-VIBE-512_07.webp?alt=media&token=f00ee1d7-f7cf-401d-b603-c4e3b31c8d63",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/GOOD-VIBE-512_08.webp?alt=media&token=b031e433-e80f-462f-844b-5bbbc445bb74",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/GOOD-VIBE-512_09.webp?alt=media&token=df85d79b-e4b8-446e-a98f-4f05b0668465",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/GOOD-VIBE-512_10.webp?alt=media&token=d0794171-2b12-4826-a9a7-74ff0b233c35",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/heart-icon.webp?alt=media&token=deb4391d-b400-48e2-b2c8-35e701c68e2d",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/just-relax.webp?alt=media&token=fc5777ce-9f8e-46f9-96de-7246b45707c2",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/keep-going.webp?alt=media&token=39ea5d9b-5e28-455e-bc9b-6993687b04bb",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/lets-go.webp?alt=media&token=a7172a6f-eda8-4178-b309-2517662de10e",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/lightbulbicon.webp?alt=media&token=fb80a5da-8ca6-4c38-afb8-f753fbb8d91f",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/live-and-maintain.webp?alt=media&token=94881926-bcee-411d-81f9-f36912b7b767",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/remind_to_smile.webp?alt=media&token=04948ae8-5e9a-409e-859c-605d1ede4f3b",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/shine-a-light.webp?alt=media&token=cf1a5f21-60c2-4154-b474-98fd4a43fc8c",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/step-by-step.webp?alt=media&token=195271cc-71b7-4561-a96d-439ecaa13fa3",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/stress-free-zone.webp?alt=media&token=523b2350-194e-4717-bceb-c04dc2b0366a",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/take-time.webp?alt=media&token=7f9ff4c3-d9fd-40d2-91aa-4c12a736f953",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/tray_Blessed.webp?alt=media&token=29ef37cb-df01-4720-8f1a-1618a1675922",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/we-mov.webp?alt=media&token=639af564-8853-42cd-b896-ebd06d8f66c0",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/world_energy.webp?alt=media&token=ce023949-3189-4f0f-97b7-ec14820e1dd1",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/you-got-this.webp?alt=media&token=3da5361b-00bb-4616-a05e-a7d3a92c77eb",
      "https://firebasestorage.googleapis.com/v0/b/sticker-f4a86.appspot.com/o/zen.webp?alt=media&token=6dfce734-5266-4e80-aa61-7b31df87bf93"
    };
    List<Sticker> stickerList = new ArrayList<>();
    for (String s : stickerURLRef) {
      stickerList.add(new Sticker(s));
    }
    return stickerList;
  }
}
