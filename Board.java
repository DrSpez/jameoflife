public class Board {
    int[] size;
    Cell[][] space;
    int nEpochs;

    public Board(int nx, int ny, int init_value) {
        size = new int[] {nx, ny};
        space = new Cell[nx][ny];
        initialize(init_value);
    }

    public void initialize(int value) {
        nEpochs = 0;
        for (int i=0; i < size[0]; i++){
            for (int j=0; j < size[1]; j++){
                int init_value;
                if (value == -1) {
                    init_value = (int)(Math.random() * 2);
                }
                else {
                    init_value = value;
                }
                space[i][j] = new Cell(init_value != 0);
            }
        }
    }

    public int countNeighbours(int i, int j) {
        int[][] neighbours = getNeighbours(i, j);
        int n_neighbours = 0;
        for (int in=0; in < 8; in++) {
            int nx = neighbours[in][0];
            int ny = neighbours[in][1];
            if (nx != -1 && ny != -1 && nx != size[0] && ny != size[1]) {
                n_neighbours += this.space[nx][ny].alive ? 1 : 0;
            }
        }
        return n_neighbours;
    }

    public static int[][] getNeighbours(int i, int j) {
        int neighbours[][] = new int[][] {{i-1, j-1}, {i, j-1}, {i+1, j-1},
                                          {i-1,   j}, {i+1,   j},
                                          {i-1, j+1}, {i, j+1}, {i+1, j+1}};
        return neighbours;
    }

    public void evolve() {

        Cell[][] new_space = new Cell[size[0]][size[1]];

        for (int j=0; j < size[1]; j++) {
            for (int i=0; i < size[0]; i++) {
                new_space[i][j] = new Cell(false);
                int n_neighbours = countNeighbours(i, j);

                if (space[i][j].alive) {
//                    Live cell death
                    if (n_neighbours < 2 || n_neighbours > 3) {
                        new_space[i][j].kill();
                    }
                    else {new_space[i][j].create();
                    }
                }
                else {
//                    Dead cell reproduction
                    if (n_neighbours == 3) {
                        new_space[i][j].create();
                    }
                    else {new_space[i][j].kill();
                    }
                }
            }
        }
        this.space = new_space;
        this.nEpochs += 1;
    }

    public void consoleShow() {
        for (int j=0; j < size[1]; j++) {
            for (int i=0; i < size[0]; i++) {
                int value = space[i][j].alive ? 1 : 0;
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
