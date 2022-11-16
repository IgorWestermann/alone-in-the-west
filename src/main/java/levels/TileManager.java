package levels;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import levels.Tile;

public class TileManager {
    private Game game;
//    private BufferedImage tile;
    BufferedImage imgGrass;
    int[][] mapTileNum;


    public TileManager(Game game) {
        this.game = game;

//        tile = new BufferedImage[10];
        mapTileNum = new int[50][50];
        loadTiles();
        loadMap();
    }

    public void loadMap() {

        InputStream is = getClass().getResourceAsStream("/maps/map.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            for (int y = 0; y < 50; y++) {
                String s = br.readLine();
                String[] nums = s.split(" ");
                for (int x = 0; x < 50; x++) {
                    mapTileNum[x][y] = Integer.parseInt(nums[x]);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadTiles() {
        String[] sprite_list = {
                "/tiles/grass.png",
                "/tiles/wall.png",
                "/tiles/water.png",
                "/tiles/earth.png",
                "/tiles/tree.png",
                "/tiles/sand.png",
        };
        InputStream grass = getClass().getResourceAsStream("/tiles/grass.png");

        try {
            imgGrass = ImageIO.read(grass);
//            tile = new BufferedImage();
//            for (int i = 0; i < 6; i++) {
//                tile = imgGrass;
//                tile[i] = new Tile();
//                tile[i].image = ImageIO.read(getClass().getResourceAsStream(sprite_list[i]));
//            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("entrou");

        }
    }

    public void paint(Graphics2D g) {

        int screenX = 0;
        int screenY = 0;
        g.drawImage(imgGrass,screenX,screenY, 32, 32,null);

        for (int j = 0; j < 50; j++) {
            for (int i = 0; i < 50; i++) {
                int tileNum = mapTileNum[i][j];
//                int worldX = i * Game.TILE_SIZE;
//                screenX = worldX - game.player.worldX + game.player.screenX;
            }
//            int worldY = j * game.TILE_SIZE;
//            screenY = worldY - game.player.worldY + game.player.screenY;


        }

    }
}

