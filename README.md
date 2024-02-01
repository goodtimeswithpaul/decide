# DECIDE

## Intercepter Launch Algorithm

### Description
The program DECIDE is implemented in a function called Main(), which is a hypothetical anti-ballistic missile system which determines whether an interceptor should be launched based upon input radar tracking information. 

The program evaluates 15 Launch Intercept Conditions (LICs) with an input set of up to 100 planar data points. A Logical Connector Matrix (LCM) determines how all 15 LICs must be considered jointly, containig elements ANDD, ORR or NOTUSED. A Preliminary Unlocking Vector (PUV) determines which LIC matters in deciding whether to launch or not. 

### Input

**NUMPOINTS** The number of planar data points.

**POINTS** Array containing the coordinates of data points.

**PARAMETERS** Struct holding parameters for LIC’s.

**LCM** Logical Connector Matrix.

**PUV** Preliminary Unlocking Vector.

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
TODO