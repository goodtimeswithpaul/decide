# DECIDE

## Intercepter Launch Algorithm

### Description
The program DECIDE is implemented in a function called Main(), which is a hypothetical anti-ballistic missile system which determines whether an interceptor should be launched based upon input radar tracking information. 

The program evaluates 15 Launch Intercept Conditions (LICs) with an input set of up to 100 planar data points. A Logical Connector Matrix (LCM) determines how all 15 LICs must be considered jointly, containig elements ANDD, ORR or NOTUSED. A Preliminary Unlocking Vector (PUV) determines which LIC matters in deciding whether to launch or not.

### Instructions
To run the the program, clone the repository using the following command.

`git clone https://github.com/goodtimeswithpaul/decide.git`

Open the project in VS Code, press play button on main file.

### Input
**NUMPOINTS** The number of planar data points.

**POINTS** Array containing the coordinates of data points.

**LCM** Logical Connector Matrix.

**PUV** Preliminary Unlocking Vector.

**Parameters: Struct holding parameters for LIC’s.**

1. double LENGTH1;
2. double RADIUS1;
3. double EPSILON;
4. double AREA1;
5. int Q_PTS;
6. int QUADS;
7. double DIST;
8. int N_PTS ;
9. int K_PTS ;
10. int A_PTS ;
11. int B_PTS ;
12. int C_PTS ;
13. int D_PTS ;
14. int E_PTS ;
15. int F_PTS ;
16. int G_PTS ;
17. double LENGTH2;
18. double RADUIS2;
19. double AREA2;

### Output

**LAUNCH** Final launch / no launch decision encoded as ”YES”, ”NO” on the standard output.

### Intermediate results
**CMV** Conditions Met Vector.

**PUM** Preliminary Unlocking Matrix.

**FUV** Final Unlocking Vector.

### Authors

Anneli Bogren – [annelibogren](https://github.com/annelibogren)

Paul Tissot-Daguette – [goodtimeswithpaul](https://github.com/goodtimeswithpaul)

Carl Wang - [WarlCang](https://github.com/WarlCang)

Rikard Johansson - [ItsRkaj](https://github.com/ItsRkaj)


## Statement of Contributions
We distributed the work in the following way:

**Anneli**: LIC1, LIC6, LIC8, LIC13, unit testing set up, README

**Paul**: LIC0, LIC5, LIC7, LIC12, combiner function for PUM and PUV into FUV

**Carl**: LIC2, LIC4, LIC9, LIC11, parameters set up, combiner function for CMV and LCM to PUM

**Rikard**: LIC3, LIC10, LIC14, combine into main