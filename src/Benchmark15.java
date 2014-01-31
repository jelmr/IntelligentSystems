/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 26/01/14
 */
public class Benchmark15 {

	public static final Bot15[] BOTS = {new LookaheadBot15(), new BullyBot15(), new CarnageBot15(), new CompetitionBot15(),
			new NeuralBot15(new double[]{0.0001, 0.8292, 0.2878, 0.1700, 0.9223, 0.3534, 0.6238, 0.8842, 0.0514, 0.4409, 0.6419, 0.0001, 0.4749, 0.0370, 0.6164, 0.0001, 0.3312, 0.5098, 0.1843, 0.7513, 0.0024, 0.6263, 0.7090, 0.0236, 0.8050, 0.6372, 0.6552, 0.0129, 0.5011, 0.6545, 0.3188, 0.0759, 0.2845, 0.8128, 0.1569, 0.2490, 0.2809, 0.7445, 0.2860, 0.4274, 0.5198, 0.7047, 0.8838, 0.1720, 0.2834, 0.1763, 0.7750, 0.4687, 0.3481, 0.2865, 0.0001, 0.0275, 0.7649, 0.1248, 0.1661, 0.7092, 0.6214, 0.8770, 0.9323, 0.9685, 0.0437, 0.4134, 0.5578, 0.2914, 0.0001, 0.1578, 0.0001, 0.9905, 0.9739, 0.9476, 0.8501, 0.3754, 0.5208, 0.6927, 0.6280, 0.6777, 0.2808, 0.4607, 0.9520, 0.9923, 0.9860, 0.0001, 0.0194, 0.4315, 0.5588, 0.8782, 0.8798, 0.6555, 0.9380, 0.0046, 0.9388, 0.2472, 0.2676, 0.4772, 0.2180, 0.2129, 0.8327, 0.7043, 0.4569, 0.0001, 0.5859, 0.9444, 0.0095, 0.4042, 0.9940, 0.1763, 0.2085, 0.5967, 0.8247, 0.6364, 0.6023, 0.0001, 0.1635, 0.2080, 0.8632, 0.3444, 0.2479, 0.7381, 0.4691, 0.1444, 0.7746, 0.5185, 0.4536, 0.7965, 0.0001, 0.8464, 0.4303, 0.7437, 0.1315, 0.2345, 0.0001, 0.7250, 0.4911, 0.0001, 0.9927, 0.8520, 0.3010, 0.5772, 0.6834, 0.9482, 0.8096, 0.8980, 0.1668, 0.9816, 0.2799, 0.7405, 0.0001, 0.0001, 0.1871, 0.0001, 0.0259, 0.8495, 0.6483, 0.9213, 0.0596, 0.9034, 0.0985, 0.4422, 0.3016, 0.5490, 0.2584, 0.7172, 0.3443, 0.0001, 0.0563, 0.0001, 0.8996, 0.6032, 0.4251, 0.2798, 0.0001})};
	public static final String[] MAPS = {
			"tools/map/map1.txt",
			"tools/map/map10.txt",
			"tools/map/map100.txt",
			"tools/map/map1000.txt",
			"tools/map/map101.txt",
			"tools/map/map102.txt",
			"tools/map/map103.txt",
			"tools/map/map104.txt",
			"tools/map/map105.txt",
			"tools/map/map106.txt",
			"tools/map/map107.txt",
			"tools/map/map108.txt",
			"tools/map/map109.txt",
			"tools/map/map11.txt",
			"tools/map/map110.txt",
			"tools/map/map111.txt",
			"tools/map/map112.txt",
			"tools/map/map113.txt",
			"tools/map/map114.txt",
			"tools/map/map115.txt",
			"tools/map/map116.txt",
			"tools/map/map117.txt",
			"tools/map/map118.txt",
			"tools/map/map119.txt",
			"tools/map/map12.txt",
			"tools/map/map120.txt",
			"tools/map/map121.txt",
			"tools/map/map122.txt",
			"tools/map/map123.txt",
			"tools/map/map124.txt",
			"tools/map/map125.txt",
			"tools/map/map126.txt",
			"tools/map/map127.txt",
			"tools/map/map128.txt",
			"tools/map/map129.txt",
			"tools/map/map13.txt",
			"tools/map/map130.txt",
			"tools/map/map131.txt",
			"tools/map/map132.txt",
			"tools/map/map133.txt",
			"tools/map/map134.txt",
			"tools/map/map135.txt",
			"tools/map/map136.txt",
			"tools/map/map137.txt",
			"tools/map/map138.txt",
			"tools/map/map139.txt",
			"tools/map/map14.txt",
			"tools/map/map140.txt",
			"tools/map/map141.txt",
			"tools/map/map142.txt",
			"tools/map/map143.txt",
			"tools/map/map144.txt",
			"tools/map/map145.txt",
			"tools/map/map146.txt",
			"tools/map/map147.txt",
			"tools/map/map148.txt",
			"tools/map/map149.txt",
			"tools/map/map15.txt",
			"tools/map/map150.txt",
			"tools/map/map151.txt",
			"tools/map/map152.txt",
			"tools/map/map153.txt",
			"tools/map/map154.txt",
			"tools/map/map155.txt",
			"tools/map/map156.txt",
			"tools/map/map157.txt",
			"tools/map/map158.txt",
			"tools/map/map159.txt",
			"tools/map/map16.txt",
			"tools/map/map160.txt",
			"tools/map/map161.txt",
			"tools/map/map162.txt",
			"tools/map/map163.txt",
			"tools/map/map164.txt",
			"tools/map/map165.txt",
			"tools/map/map166.txt",
			"tools/map/map167.txt",
			"tools/map/map168.txt",
			"tools/map/map169.txt",
			"tools/map/map17.txt",
			"tools/map/map170.txt",
			"tools/map/map171.txt",
			"tools/map/map172.txt",
			"tools/map/map173.txt",
			"tools/map/map174.txt",
			"tools/map/map175.txt",
			"tools/map/map176.txt",
			"tools/map/map177.txt",
			"tools/map/map178.txt",
			"tools/map/map179.txt",
			"tools/map/map18.txt",
			"tools/map/map180.txt",
			"tools/map/map181.txt",
			"tools/map/map182.txt",
			"tools/map/map183.txt",
			"tools/map/map184.txt",
			"tools/map/map185.txt",
			"tools/map/map186.txt",
			"tools/map/map187.txt",
			"tools/map/map188.txt",
			"tools/map/map189.txt",
			"tools/map/map19.txt",
			"tools/map/map190.txt",
			"tools/map/map191.txt",
			"tools/map/map192.txt",
			"tools/map/map193.txt",
			"tools/map/map194.txt",
			"tools/map/map195.txt",
			"tools/map/map196.txt",
			"tools/map/map197.txt",
			"tools/map/map198.txt",
			"tools/map/map199.txt",
			"tools/map/map2.txt",
			"tools/map/map20.txt",
			"tools/map/map200.txt",
			"tools/map/map201.txt",
			"tools/map/map202.txt",
			"tools/map/map203.txt",
			"tools/map/map204.txt",
			"tools/map/map205.txt",
			"tools/map/map206.txt",
			"tools/map/map207.txt",
			"tools/map/map208.txt",
			"tools/map/map209.txt",
			"tools/map/map21.txt",
			"tools/map/map210.txt",
			"tools/map/map211.txt",
			"tools/map/map212.txt",
			"tools/map/map213.txt",
			"tools/map/map214.txt",
			"tools/map/map215.txt",
			"tools/map/map216.txt",
			"tools/map/map217.txt",
			"tools/map/map218.txt",
			"tools/map/map219.txt",
			"tools/map/map22.txt",
			"tools/map/map220.txt",
			"tools/map/map221.txt",
			"tools/map/map222.txt",
			"tools/map/map223.txt",
			"tools/map/map224.txt",
			"tools/map/map225.txt",
			"tools/map/map226.txt",
			"tools/map/map227.txt",
			"tools/map/map228.txt",
			"tools/map/map229.txt",
			"tools/map/map23.txt",
			"tools/map/map230.txt",
			"tools/map/map231.txt",
			"tools/map/map232.txt",
			"tools/map/map233.txt",
			"tools/map/map234.txt",
			"tools/map/map235.txt",
			"tools/map/map236.txt",
			"tools/map/map237.txt",
			"tools/map/map238.txt",
			"tools/map/map239.txt",
			"tools/map/map24.txt",
			"tools/map/map240.txt",
			"tools/map/map241.txt",
			"tools/map/map242.txt",
			"tools/map/map243.txt",
			"tools/map/map244.txt",
			"tools/map/map245.txt",
			"tools/map/map246.txt",
			"tools/map/map247.txt",
			"tools/map/map248.txt",
			"tools/map/map249.txt",
			"tools/map/map25.txt",
			"tools/map/map250.txt",
			"tools/map/map251.txt",
			"tools/map/map252.txt",
			"tools/map/map253.txt",
			"tools/map/map254.txt",
			"tools/map/map255.txt",
			"tools/map/map256.txt",
			"tools/map/map257.txt",
			"tools/map/map258.txt",
			"tools/map/map259.txt",
			"tools/map/map26.txt",
			"tools/map/map260.txt",
			"tools/map/map261.txt",
			"tools/map/map262.txt",
			"tools/map/map263.txt",
			"tools/map/map264.txt",
			"tools/map/map265.txt",
			"tools/map/map266.txt",
			"tools/map/map267.txt",
			"tools/map/map268.txt",
			"tools/map/map269.txt",
			"tools/map/map27.txt",
			"tools/map/map270.txt",
			"tools/map/map271.txt",
			"tools/map/map272.txt",
			"tools/map/map273.txt",
			"tools/map/map274.txt",
			"tools/map/map275.txt",
			"tools/map/map276.txt",
			"tools/map/map277.txt",
			"tools/map/map278.txt",
			"tools/map/map279.txt",
			"tools/map/map28.txt",
			"tools/map/map280.txt",
			"tools/map/map281.txt",
			"tools/map/map282.txt",
			"tools/map/map283.txt",
			"tools/map/map284.txt",
			"tools/map/map285.txt",
			"tools/map/map286.txt",
			"tools/map/map287.txt",
			"tools/map/map288.txt",
			"tools/map/map289.txt",
			"tools/map/map29.txt",
			"tools/map/map290.txt",
			"tools/map/map291.txt",
			"tools/map/map292.txt",
			"tools/map/map293.txt",
			"tools/map/map294.txt",
			"tools/map/map295.txt",
			"tools/map/map296.txt",
			"tools/map/map297.txt",
			"tools/map/map298.txt",
			"tools/map/map299.txt",
			"tools/map/map3.txt",
			"tools/map/map30.txt",
			"tools/map/map300.txt",
			"tools/map/map301.txt",
			"tools/map/map302.txt",
			"tools/map/map303.txt",
			"tools/map/map304.txt",
			"tools/map/map305.txt",
			"tools/map/map306.txt",
			"tools/map/map307.txt",
			"tools/map/map308.txt",
			"tools/map/map309.txt",
			"tools/map/map31.txt",
			"tools/map/map310.txt",
			"tools/map/map311.txt",
			"tools/map/map312.txt",
			"tools/map/map313.txt",
			"tools/map/map314.txt",
			"tools/map/map315.txt",
			"tools/map/map316.txt",
			"tools/map/map317.txt",
			"tools/map/map318.txt",
			"tools/map/map319.txt",
			"tools/map/map32.txt",
			"tools/map/map320.txt",
			"tools/map/map321.txt",
			"tools/map/map322.txt",
			"tools/map/map323.txt",
			"tools/map/map324.txt",
			"tools/map/map325.txt",
			"tools/map/map326.txt",
			"tools/map/map327.txt",
			"tools/map/map328.txt",
			"tools/map/map329.txt",
			"tools/map/map33.txt",
			"tools/map/map330.txt",
			"tools/map/map331.txt",
			"tools/map/map332.txt",
			"tools/map/map333.txt",
			"tools/map/map334.txt",
			"tools/map/map335.txt",
			"tools/map/map336.txt",
			"tools/map/map337.txt",
			"tools/map/map338.txt",
			"tools/map/map339.txt",
			"tools/map/map34.txt",
			"tools/map/map340.txt",
			"tools/map/map341.txt",
			"tools/map/map342.txt",
			"tools/map/map343.txt",
			"tools/map/map344.txt",
			"tools/map/map345.txt",
			"tools/map/map346.txt",
			"tools/map/map347.txt",
			"tools/map/map348.txt",
			"tools/map/map349.txt",
			"tools/map/map35.txt",
			"tools/map/map350.txt",
			"tools/map/map351.txt",
			"tools/map/map352.txt",
			"tools/map/map353.txt",
			"tools/map/map354.txt",
			"tools/map/map355.txt",
			"tools/map/map356.txt",
			"tools/map/map357.txt",
			"tools/map/map358.txt",
			"tools/map/map359.txt",
			"tools/map/map36.txt",
			"tools/map/map360.txt",
			"tools/map/map361.txt",
			"tools/map/map362.txt",
			"tools/map/map363.txt",
			"tools/map/map364.txt",
			"tools/map/map365.txt",
			"tools/map/map366.txt",
			"tools/map/map367.txt",
			"tools/map/map368.txt",
			"tools/map/map369.txt",
			"tools/map/map37.txt",
			"tools/map/map370.txt",
			"tools/map/map371.txt",
			"tools/map/map372.txt",
			"tools/map/map373.txt",
			"tools/map/map374.txt",
			"tools/map/map375.txt",
			"tools/map/map376.txt",
			"tools/map/map377.txt",
			"tools/map/map378.txt",
			"tools/map/map379.txt",
			"tools/map/map38.txt",
			"tools/map/map380.txt",
			"tools/map/map381.txt",
			"tools/map/map382.txt",
			"tools/map/map383.txt",
			"tools/map/map384.txt",
			"tools/map/map385.txt",
			"tools/map/map386.txt",
			"tools/map/map387.txt",
			"tools/map/map388.txt",
			"tools/map/map389.txt",
			"tools/map/map39.txt",
			"tools/map/map390.txt",
			"tools/map/map391.txt",
			"tools/map/map392.txt",
			"tools/map/map393.txt",
			"tools/map/map394.txt",
			"tools/map/map395.txt",
			"tools/map/map396.txt",
			"tools/map/map397.txt",
			"tools/map/map398.txt",
			"tools/map/map399.txt",
			"tools/map/map4.txt",
			"tools/map/map40.txt",
			"tools/map/map400.txt",
			"tools/map/map401.txt",
			"tools/map/map402.txt",
			"tools/map/map403.txt",
			"tools/map/map404.txt",
			"tools/map/map405.txt",
			"tools/map/map406.txt",
			"tools/map/map407.txt",
			"tools/map/map408.txt",
			"tools/map/map409.txt",
			"tools/map/map41.txt",
			"tools/map/map410.txt",
			"tools/map/map411.txt",
			"tools/map/map412.txt",
			"tools/map/map413.txt",
			"tools/map/map414.txt",
			"tools/map/map415.txt",
			"tools/map/map416.txt",
			"tools/map/map417.txt",
			"tools/map/map418.txt",
			"tools/map/map419.txt",
			"tools/map/map42.txt",
			"tools/map/map420.txt",
			"tools/map/map421.txt",
			"tools/map/map422.txt",
			"tools/map/map423.txt",
			"tools/map/map424.txt",
			"tools/map/map425.txt",
			"tools/map/map426.txt",
			"tools/map/map427.txt",
			"tools/map/map428.txt",
			"tools/map/map429.txt",
			"tools/map/map43.txt",
			"tools/map/map430.txt",
			"tools/map/map431.txt",
			"tools/map/map432.txt",
			"tools/map/map433.txt",
			"tools/map/map434.txt",
			"tools/map/map435.txt",
			"tools/map/map436.txt",
			"tools/map/map437.txt",
			"tools/map/map438.txt",
			"tools/map/map439.txt",
			"tools/map/map44.txt",
			"tools/map/map440.txt",
			"tools/map/map441.txt",
			"tools/map/map442.txt",
			"tools/map/map443.txt",
			"tools/map/map444.txt",
			"tools/map/map445.txt",
			"tools/map/map446.txt",
			"tools/map/map447.txt",
			"tools/map/map448.txt",
			"tools/map/map449.txt",
			"tools/map/map45.txt",
			"tools/map/map450.txt",
			"tools/map/map451.txt",
			"tools/map/map452.txt",
			"tools/map/map453.txt",
			"tools/map/map454.txt",
			"tools/map/map455.txt",
			"tools/map/map456.txt",
			"tools/map/map457.txt",
			"tools/map/map458.txt",
			"tools/map/map459.txt",
			"tools/map/map46.txt",
			"tools/map/map460.txt",
			"tools/map/map461.txt",
			"tools/map/map462.txt",
			"tools/map/map463.txt",
			"tools/map/map464.txt",
			"tools/map/map465.txt",
			"tools/map/map466.txt",
			"tools/map/map467.txt",
			"tools/map/map468.txt",
			"tools/map/map469.txt",
			"tools/map/map47.txt",
			"tools/map/map470.txt",
			"tools/map/map471.txt",
			"tools/map/map472.txt",
			"tools/map/map473.txt",
			"tools/map/map474.txt",
			"tools/map/map475.txt",
			"tools/map/map476.txt",
			"tools/map/map477.txt",
			"tools/map/map478.txt",
			"tools/map/map479.txt",
			"tools/map/map48.txt",
			"tools/map/map480.txt",
			"tools/map/map481.txt",
			"tools/map/map482.txt",
			"tools/map/map483.txt",
			"tools/map/map484.txt",
			"tools/map/map485.txt",
			"tools/map/map486.txt",
			"tools/map/map487.txt",
			"tools/map/map488.txt",
			"tools/map/map489.txt",
			"tools/map/map49.txt",
			"tools/map/map490.txt",
			"tools/map/map491.txt",
			"tools/map/map492.txt",
			"tools/map/map493.txt",
			"tools/map/map494.txt",
			"tools/map/map495.txt",
			"tools/map/map496.txt",
			"tools/map/map497.txt",
			"tools/map/map498.txt",
			"tools/map/map499.txt",
			"tools/map/map5.txt",
			"tools/map/map50.txt",
			"tools/map/map500.txt",
			"tools/map/map501.txt",
			"tools/map/map502.txt",
			"tools/map/map503.txt",
			"tools/map/map504.txt",
			"tools/map/map505.txt",
			"tools/map/map506.txt",
			"tools/map/map507.txt",
			"tools/map/map508.txt",
			"tools/map/map509.txt",
			"tools/map/map51.txt",
			"tools/map/map510.txt",
			"tools/map/map511.txt",
			"tools/map/map512.txt",
			"tools/map/map513.txt",
			"tools/map/map514.txt",
			"tools/map/map515.txt",
			"tools/map/map516.txt",
			"tools/map/map517.txt",
			"tools/map/map518.txt",
			"tools/map/map519.txt",
			"tools/map/map52.txt",
			"tools/map/map520.txt",
			"tools/map/map521.txt",
			"tools/map/map522.txt",
			"tools/map/map523.txt",
			"tools/map/map524.txt",
			"tools/map/map525.txt",
			"tools/map/map526.txt",
			"tools/map/map527.txt",
			"tools/map/map528.txt",
			"tools/map/map529.txt",
			"tools/map/map53.txt",
			"tools/map/map530.txt",
			"tools/map/map531.txt",
			"tools/map/map532.txt",
			"tools/map/map533.txt",
			"tools/map/map534.txt",
			"tools/map/map535.txt",
			"tools/map/map536.txt",
			"tools/map/map537.txt",
			"tools/map/map538.txt",
			"tools/map/map539.txt",
			"tools/map/map54.txt",
			"tools/map/map540.txt",
			"tools/map/map541.txt",
			"tools/map/map542.txt",
			"tools/map/map543.txt",
			"tools/map/map544.txt",
			"tools/map/map545.txt",
			"tools/map/map546.txt",
			"tools/map/map547.txt",
			"tools/map/map548.txt",
			"tools/map/map549.txt",
			"tools/map/map55.txt",
			"tools/map/map550.txt",
			"tools/map/map551.txt",
			"tools/map/map552.txt",
			"tools/map/map553.txt",
			"tools/map/map554.txt",
			"tools/map/map555.txt",
			"tools/map/map556.txt",
			"tools/map/map557.txt",
			"tools/map/map558.txt",
			"tools/map/map559.txt",
			"tools/map/map56.txt",
			"tools/map/map560.txt",
			"tools/map/map561.txt",
			"tools/map/map562.txt",
			"tools/map/map563.txt",
			"tools/map/map564.txt",
			"tools/map/map565.txt",
			"tools/map/map566.txt",
			"tools/map/map567.txt",
			"tools/map/map568.txt",
			"tools/map/map569.txt",
			"tools/map/map57.txt",
			"tools/map/map570.txt",
			"tools/map/map571.txt",
			"tools/map/map572.txt",
			"tools/map/map573.txt",
			"tools/map/map574.txt",
			"tools/map/map575.txt",
			"tools/map/map576.txt",
			"tools/map/map577.txt",
			"tools/map/map578.txt",
			"tools/map/map579.txt",
			"tools/map/map58.txt",
			"tools/map/map580.txt",
			"tools/map/map581.txt",
			"tools/map/map582.txt",
			"tools/map/map583.txt",
			"tools/map/map584.txt",
			"tools/map/map585.txt",
			"tools/map/map586.txt",
			"tools/map/map587.txt",
			"tools/map/map588.txt",
			"tools/map/map589.txt",
			"tools/map/map59.txt",
			"tools/map/map590.txt",
			"tools/map/map591.txt",
			"tools/map/map592.txt",
			"tools/map/map593.txt",
			"tools/map/map594.txt",
			"tools/map/map595.txt",
			"tools/map/map596.txt",
			"tools/map/map597.txt",
			"tools/map/map598.txt",
			"tools/map/map599.txt",
			"tools/map/map6.txt",
			"tools/map/map60.txt",
			"tools/map/map600.txt",
			"tools/map/map601.txt",
			"tools/map/map602.txt",
			"tools/map/map603.txt",
			"tools/map/map604.txt",
			"tools/map/map605.txt",
			"tools/map/map606.txt",
			"tools/map/map607.txt",
			"tools/map/map608.txt",
			"tools/map/map609.txt",
			"tools/map/map61.txt",
			"tools/map/map610.txt",
			"tools/map/map611.txt",
			"tools/map/map612.txt",
			"tools/map/map613.txt",
			"tools/map/map614.txt",
			"tools/map/map615.txt",
			"tools/map/map616.txt",
			"tools/map/map617.txt",
			"tools/map/map618.txt",
			"tools/map/map619.txt",
			"tools/map/map62.txt",
			"tools/map/map620.txt",
			"tools/map/map621.txt",
			"tools/map/map622.txt",
			"tools/map/map623.txt",
			"tools/map/map624.txt",
			"tools/map/map625.txt",
			"tools/map/map626.txt",
			"tools/map/map627.txt",
			"tools/map/map628.txt",
			"tools/map/map629.txt",
			"tools/map/map63.txt",
			"tools/map/map630.txt",
			"tools/map/map631.txt",
			"tools/map/map632.txt",
			"tools/map/map633.txt",
			"tools/map/map634.txt",
			"tools/map/map635.txt",
			"tools/map/map636.txt",
			"tools/map/map637.txt",
			"tools/map/map638.txt",
			"tools/map/map639.txt",
			"tools/map/map64.txt",
			"tools/map/map640.txt",
			"tools/map/map641.txt",
			"tools/map/map642.txt",
			"tools/map/map643.txt",
			"tools/map/map644.txt",
			"tools/map/map645.txt",
			"tools/map/map646.txt",
			"tools/map/map647.txt",
			"tools/map/map648.txt",
			"tools/map/map649.txt",
			"tools/map/map65.txt",
			"tools/map/map650.txt",
			"tools/map/map651.txt",
			"tools/map/map652.txt",
			"tools/map/map653.txt",
			"tools/map/map654.txt",
			"tools/map/map655.txt",
			"tools/map/map656.txt",
			"tools/map/map657.txt",
			"tools/map/map658.txt",
			"tools/map/map659.txt",
			"tools/map/map66.txt",
			"tools/map/map660.txt",
			"tools/map/map661.txt",
			"tools/map/map662.txt",
			"tools/map/map663.txt",
			"tools/map/map664.txt",
			"tools/map/map665.txt",
			"tools/map/map666.txt",
			"tools/map/map667.txt",
			"tools/map/map668.txt",
			"tools/map/map669.txt",
			"tools/map/map67.txt",
			"tools/map/map670.txt",
			"tools/map/map671.txt",
			"tools/map/map672.txt",
			"tools/map/map673.txt",
			"tools/map/map674.txt",
			"tools/map/map675.txt",
			"tools/map/map676.txt",
			"tools/map/map677.txt",
			"tools/map/map678.txt",
			"tools/map/map679.txt",
			"tools/map/map68.txt",
			"tools/map/map680.txt",
			"tools/map/map681.txt",
			"tools/map/map682.txt",
			"tools/map/map683.txt",
			"tools/map/map684.txt",
			"tools/map/map685.txt",
			"tools/map/map686.txt",
			"tools/map/map687.txt",
			"tools/map/map688.txt",
			"tools/map/map689.txt",
			"tools/map/map69.txt",
			"tools/map/map690.txt",
			"tools/map/map691.txt",
			"tools/map/map692.txt",
			"tools/map/map693.txt",
			"tools/map/map694.txt",
			"tools/map/map695.txt",
			"tools/map/map696.txt",
			"tools/map/map697.txt",
			"tools/map/map698.txt",
			"tools/map/map699.txt",
			"tools/map/map7.txt",
			"tools/map/map70.txt",
			"tools/map/map700.txt",
			"tools/map/map701.txt",
			"tools/map/map702.txt",
			"tools/map/map703.txt",
			"tools/map/map704.txt",
			"tools/map/map705.txt",
			"tools/map/map706.txt",
			"tools/map/map707.txt",
			"tools/map/map708.txt",
			"tools/map/map709.txt",
			"tools/map/map71.txt",
			"tools/map/map710.txt",
			"tools/map/map711.txt",
			"tools/map/map712.txt",
			"tools/map/map713.txt",
			"tools/map/map714.txt",
			"tools/map/map715.txt",
			"tools/map/map716.txt",
			"tools/map/map717.txt",
			"tools/map/map718.txt",
			"tools/map/map719.txt",
			"tools/map/map72.txt",
			"tools/map/map720.txt",
			"tools/map/map721.txt",
			"tools/map/map722.txt",
			"tools/map/map723.txt",
			"tools/map/map724.txt",
			"tools/map/map725.txt",
			"tools/map/map726.txt",
			"tools/map/map727.txt",
			"tools/map/map728.txt",
			"tools/map/map729.txt",
			"tools/map/map73.txt",
			"tools/map/map730.txt",
			"tools/map/map731.txt",
			"tools/map/map732.txt",
			"tools/map/map733.txt",
			"tools/map/map734.txt",
			"tools/map/map735.txt",
			"tools/map/map736.txt",
			"tools/map/map737.txt",
			"tools/map/map738.txt",
			"tools/map/map739.txt",
			"tools/map/map74.txt",
			"tools/map/map740.txt",
			"tools/map/map741.txt",
			"tools/map/map742.txt",
			"tools/map/map743.txt",
			"tools/map/map744.txt",
			"tools/map/map745.txt",
			"tools/map/map746.txt",
			"tools/map/map747.txt",
			"tools/map/map748.txt",
			"tools/map/map749.txt",
			"tools/map/map75.txt",
			"tools/map/map750.txt",
			"tools/map/map751.txt",
			"tools/map/map752.txt",
			"tools/map/map753.txt",
			"tools/map/map754.txt",
			"tools/map/map755.txt",
			"tools/map/map756.txt",
			"tools/map/map757.txt",
			"tools/map/map758.txt",
			"tools/map/map759.txt",
			"tools/map/map76.txt",
			"tools/map/map760.txt",
			"tools/map/map761.txt",
			"tools/map/map762.txt",
			"tools/map/map763.txt",
			"tools/map/map764.txt",
			"tools/map/map765.txt",
			"tools/map/map766.txt",
			"tools/map/map767.txt",
			"tools/map/map768.txt",
			"tools/map/map769.txt",
			"tools/map/map77.txt",
			"tools/map/map770.txt",
			"tools/map/map771.txt",
			"tools/map/map772.txt",
			"tools/map/map773.txt",
			"tools/map/map774.txt",
			"tools/map/map775.txt",
			"tools/map/map776.txt",
			"tools/map/map777.txt",
			"tools/map/map778.txt",
			"tools/map/map779.txt",
			"tools/map/map78.txt",
			"tools/map/map780.txt",
			"tools/map/map781.txt",
			"tools/map/map782.txt",
			"tools/map/map783.txt",
			"tools/map/map784.txt",
			"tools/map/map785.txt",
			"tools/map/map786.txt",
			"tools/map/map787.txt",
			"tools/map/map788.txt",
			"tools/map/map789.txt",
			"tools/map/map79.txt",
			"tools/map/map790.txt",
			"tools/map/map791.txt",
			"tools/map/map792.txt",
			"tools/map/map793.txt",
			"tools/map/map794.txt",
			"tools/map/map795.txt",
			"tools/map/map796.txt",
			"tools/map/map797.txt",
			"tools/map/map798.txt",
			"tools/map/map799.txt",
			"tools/map/map8.txt",
			"tools/map/map80.txt",
			"tools/map/map800.txt",
			"tools/map/map801.txt",
			"tools/map/map802.txt",
			"tools/map/map803.txt",
			"tools/map/map804.txt",
			"tools/map/map805.txt",
			"tools/map/map806.txt",
			"tools/map/map807.txt",
			"tools/map/map808.txt",
			"tools/map/map809.txt",
			"tools/map/map81.txt",
			"tools/map/map810.txt",
			"tools/map/map811.txt",
			"tools/map/map812.txt",
			"tools/map/map813.txt",
			"tools/map/map814.txt",
			"tools/map/map815.txt",
			"tools/map/map816.txt",
			"tools/map/map817.txt",
			"tools/map/map818.txt",
			"tools/map/map819.txt",
			"tools/map/map82.txt",
			"tools/map/map820.txt",
			"tools/map/map821.txt",
			"tools/map/map822.txt",
			"tools/map/map823.txt",
			"tools/map/map824.txt",
			"tools/map/map825.txt",
			"tools/map/map826.txt",
			"tools/map/map827.txt",
			"tools/map/map828.txt",
			"tools/map/map829.txt",
			"tools/map/map83.txt",
			"tools/map/map830.txt",
			"tools/map/map831.txt",
			"tools/map/map832.txt",
			"tools/map/map833.txt",
			"tools/map/map834.txt",
			"tools/map/map835.txt",
			"tools/map/map836.txt",
			"tools/map/map837.txt",
			"tools/map/map838.txt",
			"tools/map/map839.txt",
			"tools/map/map84.txt",
			"tools/map/map840.txt",
			"tools/map/map841.txt",
			"tools/map/map842.txt",
			"tools/map/map843.txt",
			"tools/map/map844.txt",
			"tools/map/map845.txt",
			"tools/map/map846.txt",
			"tools/map/map847.txt",
			"tools/map/map848.txt",
			"tools/map/map849.txt",
			"tools/map/map85.txt",
			"tools/map/map850.txt",
			"tools/map/map851.txt",
			"tools/map/map852.txt",
			"tools/map/map853.txt",
			"tools/map/map854.txt",
			"tools/map/map855.txt",
			"tools/map/map856.txt",
			"tools/map/map857.txt",
			"tools/map/map858.txt",
			"tools/map/map859.txt",
			"tools/map/map86.txt",
			"tools/map/map860.txt",
			"tools/map/map861.txt",
			"tools/map/map862.txt",
			"tools/map/map863.txt",
			"tools/map/map864.txt",
			"tools/map/map865.txt",
			"tools/map/map866.txt",
			"tools/map/map867.txt",
			"tools/map/map868.txt",
			"tools/map/map869.txt",
			"tools/map/map87.txt",
			"tools/map/map870.txt",
			"tools/map/map871.txt",
			"tools/map/map872.txt",
			"tools/map/map873.txt",
			"tools/map/map874.txt",
			"tools/map/map875.txt",
			"tools/map/map876.txt",
			"tools/map/map877.txt",
			"tools/map/map878.txt",
			"tools/map/map879.txt",
			"tools/map/map88.txt",
			"tools/map/map880.txt",
			"tools/map/map881.txt",
			"tools/map/map882.txt",
			"tools/map/map883.txt",
			"tools/map/map884.txt",
			"tools/map/map885.txt",
			"tools/map/map886.txt",
			"tools/map/map887.txt",
			"tools/map/map888.txt",
			"tools/map/map889.txt",
			"tools/map/map89.txt",
			"tools/map/map890.txt",
			"tools/map/map891.txt",
			"tools/map/map892.txt",
			"tools/map/map893.txt",
			"tools/map/map894.txt",
			"tools/map/map895.txt",
			"tools/map/map896.txt",
			"tools/map/map897.txt",
			"tools/map/map898.txt",
			"tools/map/map899.txt",
			"tools/map/map9.txt",
			"tools/map/map90.txt",
			"tools/map/map900.txt",
			"tools/map/map901.txt",
			"tools/map/map902.txt",
			"tools/map/map903.txt",
			"tools/map/map904.txt",
			"tools/map/map905.txt",
			"tools/map/map906.txt",
			"tools/map/map907.txt",
			"tools/map/map908.txt",
			"tools/map/map909.txt",
			"tools/map/map91.txt",
			"tools/map/map910.txt",
			"tools/map/map911.txt",
			"tools/map/map912.txt",
			"tools/map/map913.txt",
			"tools/map/map914.txt",
			"tools/map/map915.txt",
			"tools/map/map916.txt",
			"tools/map/map917.txt",
			"tools/map/map918.txt",
			"tools/map/map919.txt",
			"tools/map/map92.txt",
			"tools/map/map920.txt",
			"tools/map/map921.txt",
			"tools/map/map922.txt",
			"tools/map/map923.txt",
			"tools/map/map924.txt",
			"tools/map/map925.txt",
			"tools/map/map926.txt",
			"tools/map/map927.txt",
			"tools/map/map928.txt",
			"tools/map/map929.txt",
			"tools/map/map93.txt",
			"tools/map/map930.txt",
			"tools/map/map931.txt",
			"tools/map/map932.txt",
			"tools/map/map933.txt",
			"tools/map/map934.txt",
			"tools/map/map935.txt",
			"tools/map/map936.txt",
			"tools/map/map937.txt",
			"tools/map/map938.txt",
			"tools/map/map939.txt",
			"tools/map/map94.txt",
			"tools/map/map940.txt",
			"tools/map/map941.txt",
			"tools/map/map942.txt",
			"tools/map/map943.txt",
			"tools/map/map944.txt",
			"tools/map/map945.txt",
			"tools/map/map946.txt",
			"tools/map/map947.txt",
			"tools/map/map948.txt",
			"tools/map/map949.txt",
			"tools/map/map95.txt",
			"tools/map/map950.txt",
			"tools/map/map951.txt",
			"tools/map/map952.txt",
			"tools/map/map953.txt",
			"tools/map/map954.txt",
			"tools/map/map955.txt",
			"tools/map/map956.txt",
			"tools/map/map957.txt",
			"tools/map/map958.txt",
			"tools/map/map959.txt",
			"tools/map/map96.txt",
			"tools/map/map960.txt",
			"tools/map/map961.txt",
			"tools/map/map962.txt",
			"tools/map/map963.txt",
			"tools/map/map964.txt",
			"tools/map/map965.txt",
			"tools/map/map966.txt",
			"tools/map/map967.txt",
			"tools/map/map968.txt",
			"tools/map/map969.txt",
			"tools/map/map97.txt",
			"tools/map/map970.txt",
			"tools/map/map971.txt",
			"tools/map/map972.txt",
			"tools/map/map973.txt",
			"tools/map/map974.txt",
			"tools/map/map975.txt",
			"tools/map/map976.txt",
			"tools/map/map977.txt",
			"tools/map/map978.txt",
			"tools/map/map979.txt",
			"tools/map/map98.txt",
			"tools/map/map980.txt",
			"tools/map/map981.txt",
			"tools/map/map982.txt",
			"tools/map/map983.txt",
			"tools/map/map984.txt",
			"tools/map/map985.txt",
			"tools/map/map986.txt",
			"tools/map/map987.txt",
			"tools/map/map988.txt",
			"tools/map/map989.txt",
			"tools/map/map99.txt",
			"tools/map/map990.txt",
			"tools/map/map991.txt",
			"tools/map/map992.txt",
			"tools/map/map993.txt",
			"tools/map/map994.txt",
			"tools/map/map995.txt",
			"tools/map/map996.txt",
			"tools/map/map997.txt",
			"tools/map/map998.txt",
			"tools/map/map999.txt",
			"tools/maps/3planets/map1.txt",
			"tools/maps/3planets/map2.txt",
			"tools/maps/3planets/map3.txt",
			"tools/maps/4planets/map1.txt",
			"tools/maps/4planets/map2.txt",
			"tools/maps/4planets/map3.txt",
			"tools/maps/5planets/map1.txt",
			"tools/maps/5planets/map2.txt",
			"tools/maps/5planets/map3.txt",
			"tools/maps/6planets/map1.txt",
			"tools/maps/6planets/map2.txt",
			"tools/maps/6planets/map3.txt",
			"tools/maps/7planets/map1.txt",
			"tools/maps/7planets/map2.txt",
			"tools/maps/7planets/map3.txt",
			"tools/maps/8planets/map1.txt",
			"tools/maps/8planets/map2.txt",
			"tools/maps/8planets/map3.txt",
			"tools/maps/larger/map1.txt",
			"tools/maps/larger/map10.txt",
			"tools/maps/larger/map11.txt",
			"tools/maps/larger/map12.txt",
			"tools/maps/larger/map2.txt",
			"tools/maps/larger/map3.txt",
			"tools/maps/larger/map4.txt",
			"tools/maps/larger/map5.txt",
			"tools/maps/larger/map6.txt",
			"tools/maps/larger/map7.txt",
			"tools/maps/larger/map8.txt",
			"tools/maps/larger/map9.txt"
	};


	public static void main(String[] args) {
		new Benchmark15().start();
	}













	private void start() {
		int totalCounter = 0;

		System.out.printf("\"mapsize\",\"Bot15\",\"Wins\",\"Draws\",\"Loses\"\n");

		for (int i = 0; i < BOTS.length; i++) {
			int[] mapSizeTotal = new int[100];
			int[] mapSizeWins = new int[100];
			int[] mapSizeDraws = new int[100];
			int[] mapSizeLoses = new int[100];

			int winCount = 0;
			int drawCount = 0;
			int lossCount = 0;

			for (int j = 0; j < MAPS.length; j++) {
				SimulatedPlanetWarsParallel15 spw = new SimulatedPlanetWarsParallel15(MAPS[j]);
				for (int k = 0; k < BOTS.length; k++) {
					if (i == k) { continue;}
					SimulatedPlanetWarsParallel15 newspw = new SimulatedPlanetWarsParallel15(spw);
					int score1 = simulate(BOTS[i], BOTS[k], newspw);

					if (score1 == 0) {
						lossCount++;
					} else if (score1 == 1) {
						drawCount++;
					} else {
						winCount++;
					}

					newspw = new SimulatedPlanetWarsParallel15(spw);
					int score2 = 2 - simulate(BOTS[k], BOTS[i], newspw);

					if (score2 == 0) {
						lossCount++;
					} else if (score2 == 1) {
						drawCount++;
					} else {
						winCount++;
					}

					mapSizeTotal[newspw.Planets().size()] = mapSizeTotal[newspw.Planets().size()] + 2;

				}
				mapSizeWins[spw.Planets().size()] += winCount;
				mapSizeDraws[spw.Planets().size()] += drawCount;
				mapSizeLoses[spw.Planets().size()] += lossCount;

			}

			double totalMatches = MAPS.length * (BOTS.length - 1) * 2;
			double wins = winCount / totalMatches;
			double draws = drawCount / totalMatches;
			double loses = lossCount / totalMatches;
			System.out.printf("\"%d\",%s,%.2f, %.2f, %.2f\n", totalCounter++, BOTS[i].getClass().getSimpleName(), wins, draws, loses);

		}

	}






	public static int simulate(Bot15 a, Bot15 b, SimulatedPlanetWarsParallel15 spw) {
		if(a.getClass().getSimpleName().equals("SimulatorBot15")){
			a = new SimulatorBot15();
		}

		if(b.getClass().getSimpleName().equals("SimulatorBot15")){
			b = new SimulatorBot15();
		}

		int turnsLeft = 100;
		int winner = -1;

		while (winner == -1 && turnsLeft-- > 0) {

			spw.player = Bot15.FRIENDLY;
			Action15 aAction = a.getAction(spw);
			spw.player = Bot15.HOSTILE;
			Action15 bAction = b.getAction(spw);

			if (aAction != null && aAction.isValid()) {
				spw.player = Bot15.FRIENDLY;
				spw.IssueOrder(aAction);
			}

			if (bAction != null && bAction.isValid()) {
				spw.player = Bot15.HOSTILE;
				spw.IssueOrder(bAction);
			}

			winner = spw.Winner();
		}

		if (winner == Bot15.FRIENDLY) { // a wins

			return 2;
		} else if (winner == Bot15.HOSTILE) {
			return 0;
		} else {
			return 1;
		}
	}



	private void start2() {
		int totalCounter = 0;

		System.out.printf("\"mapsize\",\"Bot15\",\"Wins\"\n");

		for (int i = 0; i < BOTS.length; i++) {
			int[] mapSizeTotal = new int[100];
			int[] mapSizeScores = new int[100];

			for (int j = 0; j < MAPS.length; j++) {
				int mapScore = 0;
				SimulatedPlanetWarsParallel15 spw = new SimulatedPlanetWarsParallel15(MAPS[j]);
				for (int k = 0; k < BOTS.length; k++) {
					if (i == k) { continue;}
					SimulatedPlanetWarsParallel15 newspw = new SimulatedPlanetWarsParallel15(spw);
					mapScore += simulate(BOTS[i], BOTS[k], newspw);

					newspw = new SimulatedPlanetWarsParallel15(spw);
					mapScore += (2 -simulate(BOTS[k], BOTS[i], newspw));

					mapSizeTotal[newspw.Planets().size()] = mapSizeTotal[newspw.Planets().size()] + 4;

				}
				mapSizeScores[spw.Planets().size()] = mapSizeScores[spw.Planets().size()] + mapScore;

			}

			for (int j = 0; j < mapSizeTotal.length; j++) {
				if (mapSizeTotal[j] == 0) {continue;}

				System.out.printf("\"%d\",%d,%s,%f\n", totalCounter++, j, BOTS[i].getClass().getSimpleName(), mapSizeScores[j] / ((double) mapSizeTotal[j]));

			}

		}

	}


}