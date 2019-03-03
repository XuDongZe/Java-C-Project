package Test;

import Constant.SpriteType;
import Poro.IconLabel;
import Poro.Sprite;
import Util.PoroUtil;

public class TestUtil {
	public static void main(String[] args) {
		Sprite sprite = new Sprite("testSprite", new IconLabel(), SpriteType.BACKGROUND, "这是一张背景的精灵");
		PoroUtil.saveObj("sprite.spr", sprite);
		Sprite b = (Sprite) PoroUtil.readObj("sprite.spr");
		System.out.println(b);
	}
}
