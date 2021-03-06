package lifepath;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import character.Morph;
import character.Skill;
import dice.Die;

public class RandomSkillCharts {
	
	/**
	 * Gets a random skill field, given a skill
	 * @param s - should include type of skill to assign
	 * @return whether or not a field was found
	 */
	public static boolean setRandomSkillField(Skill s) {
		String label = s.getText();
		if (label == null){
			System.out.println(s.toString());
			return false;
		}
		String newField = null;
		Class<?>[] args = null; // to make everyone happy I guess
		String functionName = "getRandom" + label + "Field";

		try {
			Method m = RandomSkillCharts.class.getDeclaredMethod(functionName, args);
			m.setAccessible(true);
			newField = (String)m.invoke(null);
			
			if (newField != null) {
				s.setField(newField);
				return true;
			}
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Gets a random academics field. Uses the table found on p.42 of Transhuman 
	 * @return string containing field
	 */
	public static String getRandomAcademicsField(){
		Die d100 = new Die(100);
		int roll = d100.Roll();
		String label = VariableRollObject.findResult(academicsFields, roll);;
		
		// Various weird cases
		if (roll >= 51 && roll <= 53){ //51�53 (Pre-Fall/Post-Fall) History
			if (d100.Roll() <= 50)
				label = "Pre-Fall " + label;
			else
				label = "Post-Fall " + label;
		} else if ((roll >= 04 && roll <= 07) || (roll >= 17 && roll <= 19) || 
				   (roll >= 45 && roll <= 47) || (roll >= 54 && roll <= 56) ||
				   (roll >= 98 && roll <= 100) ) { // (Xeno) something - many cases	
			if (d100.Roll() <= 50)
				label = "Xeno" + label.toLowerCase();
		} else if ( (roll >= 11 && roll <= 16)  || (roll >= 83 && roll <= 88) ||
				    (roll >= 96 && roll <= 97) ) { // (Astro) something
			if (d100.Roll() <= 50)
				label = "Astro" + label.toLowerCase();
		} 

		return label;
	}
	
	/**
	 * Gets a random art field. Uses the table found on p.42 of Transhuman 
	 * @return string containing field
	 */
	public static String getRandomArtField(){
		Die d100 = new Die (100);
		return VariableRollObject.findResult(artFields, d100.Roll());
	}
	
	/**
	 * Gets a random hardware field. Uses the table found on p.42 of Transhuman 
	 * @return string containing field
	 */
	public static String getRandomHardwareField(){
		Die d100 = new Die (100);
		return VariableRollObject.findResult(hardwareFields, d100.Roll());
	}
	
	/**
	 * Gets a random interest. Might roll on place, faction, or network depending 
	 * on specifics of the interest. Tables found on p 42-42 of Transhuman
	 * @return string containing field
	 */
	public static String getRandomInterestField(){
		Die d100 = new Die (100);
		int roll = d100.Roll();
		String label = VariableRollObject.findResult(interestFields, roll);
		
		if (roll >= 23 && roll <= 24){ //23, 24 Drugs/Petals/Narcoalgorithms
			Die d3 = new Die(3);
			int roll2 = d3.Roll();
			if (roll2 == 1)
				return "Drugs";
			else if (roll2 == 2)
				return "Petals";
			else
				return "Narcoalgorithms";
		}
		if (roll >= 51 && roll <= 52){ //51�52 (Topic) Mesh Forums
			if (d100.Roll() <= 25)
				label = VariableRollObject.findResult(factionFields, d100.Roll()) + label;
			else
				label = VariableRollObject.findResult(professionFields, d100.Roll()) + label;
			
		}
		if (roll >= 69 && roll <= 70) {//69�70 (Location) Places of Interest
			label = VariableRollObject.findResult(placeFields, d100.Roll()) + label;
		}
		return label;
	}
		
	/**
	 * Gets a random Profession field. Uses the table found on p.43 of Transhuman 
	 * @return string containing field
	 */
	public static String getRandomProfessionField(){
		Die d100 = new Die (100);
		return VariableRollObject.findResult(professionFields, d100.Roll());
	}

	/**
	 * Gets a random medicine field. Uses the table found on p.43 of Transhuman 
	 * @return string containing field
	 */
	public static String getRandomMedicineField(){
		Die d100 = new Die (100);
		int roll = d100.Roll();
		
		if (roll >= 91 && roll <= 95){	// 91�95 Uplifts (by type)
			Morph upliftType = LifePathCharts.getUplift();
			return "Uplift (" + upliftType.getLabel() + ")";			
		}
		return VariableRollObject.findResult(medicineFields, roll);
	}
	/**
	 * Gets a random network. Uses the table found on p.43 of Transhuman 
	 * @return string containing field
	 */
	public static String getRandomNetworkingField(){
		Die d100 = new Die (100);
		return VariableRollObject.findResult(networkFields, d100.Roll());
	}

	/**
	 * Gets a random pilot field. Uses the table found on p.43 of Transhuman 
	 * @return string containing field
	 */
	public static String getRandomPilotField(){
		Die d100 = new Die (100);
		return VariableRollObject.findResult(pilotFields, d100.Roll());
	}	

	/**
	 * Gets a random language field. Uses the table found on p.43 of Transhuman 
	 * @return string containing field
	 */
	public static String getRandomLanguageField(){
		Die d100 = new Die (100);
		return VariableRollObject.findResult(languageFields, d100.Roll());
	}

	protected static final ArrayList<VariableRollObject<String>> academicsFields = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> artFields = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> hardwareFields = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> placeFields = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> factionFields = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> interestFields = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> languageFields = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> medicineFields = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> networkFields = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> pilotFields = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> professionFields = 
			new ArrayList<VariableRollObject<String>>();
	
	static {		
		academicsFields.add(new VariableRollObject<String>(1,  3,  "Anthropology"));
		academicsFields.add(new VariableRollObject<String>(4,  7,  "Archeology"));
		academicsFields.add(new VariableRollObject<String>(8,  10, "Astronomy"));
		academicsFields.add(new VariableRollObject<String>(11, 16, "Biology"));
		academicsFields.add(new VariableRollObject<String>(17, 19, "Botany"));
		academicsFields.add(new VariableRollObject<String>(20, 24, "Chemistry"));
		academicsFields.add(new VariableRollObject<String>(25, 26, "Climatology"));
		academicsFields.add(new VariableRollObject<String>(27, 29, "Cognitive Science"));
		academicsFields.add(new VariableRollObject<String>(30, 33, "Computer Science"));
		academicsFields.add(new VariableRollObject<String>(34, 36, "Cryptography"));
		academicsFields.add(new VariableRollObject<String>(37, 38, "Ecology"));
		academicsFields.add(new VariableRollObject<String>(39, 40, "Economics"));
		academicsFields.add(new VariableRollObject<String>(41, 44, "Engineering"));
		academicsFields.add(new VariableRollObject<String>(45, 47, "Genetics"));
		academicsFields.add(new VariableRollObject<String>(48, 50, "Geology"));
		academicsFields.add(new VariableRollObject<String>(51, 53, "History"));
		academicsFields.add(new VariableRollObject<String>(54, 56, "Linguistics"));
		academicsFields.add(new VariableRollObject<String>(57, 59, "Mathematics"));
		academicsFields.add(new VariableRollObject<String>(60, 63, "Materials Science"));
		academicsFields.add(new VariableRollObject<String>(64, 66, "Memetics"));
		academicsFields.add(new VariableRollObject<String>(67, 68, "Microbiology"));
		academicsFields.add(new VariableRollObject<String>(69, 71, "Military Science"));
		academicsFields.add(new VariableRollObject<String>(72, 75, "Nanotechnology"));
		academicsFields.add(new VariableRollObject<String>(76, 79, "Neuroscience"));
		academicsFields.add(new VariableRollObject<String>(80, 82, "Philosophy"));
		academicsFields.add(new VariableRollObject<String>(83, 88, "Physics"));
		academicsFields.add(new VariableRollObject<String>(89, 91, "Political Science"));
		academicsFields.add(new VariableRollObject<String>(92, 95, "Psychology"));
		academicsFields.add(new VariableRollObject<String>(96, 97, "Sociology"));
		academicsFields.add(new VariableRollObject<String>(98, 100, "Zoology"));
		
		
		artFields.add(new VariableRollObject<String>(1,  5,  "Architecture"));
		artFields.add(new VariableRollObject<String>(6,  10, "Bodysculpting"));
		artFields.add(new VariableRollObject<String>(11, 15, "Cooking"));
		artFields.add(new VariableRollObject<String>(16, 20, "Criticism"));
		artFields.add(new VariableRollObject<String>(21, 25, "Dance"));
		artFields.add(new VariableRollObject<String>(26, 30, "Digital Art"));
		artFields.add(new VariableRollObject<String>(31, 35, "Drama"));
		artFields.add(new VariableRollObject<String>(36, 40, "Drawing"));
		artFields.add(new VariableRollObject<String>(41, 45, "Erotic Entertainment"));
		artFields.add(new VariableRollObject<String>(46, 50, "Fashion"));
		artFields.add(new VariableRollObject<String>(51, 55, "Game Design"));
		artFields.add(new VariableRollObject<String>(56, 60, "Music"));
		artFields.add(new VariableRollObject<String>(61, 65, "Painting"));
		artFields.add(new VariableRollObject<String>(66, 70, "Performance"));
		artFields.add(new VariableRollObject<String>(71, 75, "Poetry"));
		artFields.add(new VariableRollObject<String>(76, 80, "Sculpture"));
		artFields.add(new VariableRollObject<String>(81, 85, "Simulspace Design"));
		artFields.add(new VariableRollObject<String>(86, 90, "Singing"));
		artFields.add(new VariableRollObject<String>(91, 95, "Speech"));
		artFields.add(new VariableRollObject<String>(96, 100, "Writing"));
		
		hardwareFields.add(new VariableRollObject<String>(1,  13, "Aerospace"));
		hardwareFields.add(new VariableRollObject<String>(14, 21, "Armorer"));
		hardwareFields.add(new VariableRollObject<String>(22, 41, "Electronics"));
		hardwareFields.add(new VariableRollObject<String>(42, 53, "Groundcraft"));
		hardwareFields.add(new VariableRollObject<String>(54, 61, "Implants"));
		hardwareFields.add(new VariableRollObject<String>(62, 74, "Industrial"));
		hardwareFields.add(new VariableRollObject<String>(75, 82, "Nautical"));
		hardwareFields.add(new VariableRollObject<String>(83, 100, "Robotics"));
		
		placeFields.add(new VariableRollObject<String>(01, 06, "Earth"));
		placeFields.add(new VariableRollObject<String>(07, 13, "Earth Orbital"));
		placeFields.add(new VariableRollObject<String>(14, 17, "Europan"));
		placeFields.add(new VariableRollObject<String>(18, 20, "Extrasolar"));
		placeFields.add(new VariableRollObject<String>(21, 22, "Inner Fringe"));
		placeFields.add(new VariableRollObject<String>(23, 29, "Jovian"));
		placeFields.add(new VariableRollObject<String>(30, 32, "Jovian Trojans"));
		placeFields.add(new VariableRollObject<String>(33, 44, "Lunar"));
		placeFields.add(new VariableRollObject<String>(45, 52, "Main Belt"));
		placeFields.add(new VariableRollObject<String>(53, 70, "Martian"));
		placeFields.add(new VariableRollObject<String>(71, 75, "Mercurian"));
		placeFields.add(new VariableRollObject<String>(76, 77, "Neptunian"));
		placeFields.add(new VariableRollObject<String>(78, "Neptunian Trojans"));
		placeFields.add(new VariableRollObject<String>(79, 81, "Outer Fringe"));
		placeFields.add(new VariableRollObject<String>(82, 88, "Saturnian"));
		placeFields.add(new VariableRollObject<String>(89, 90, "Solar"));
		placeFields.add(new VariableRollObject<String>(91, 94, "Uranian"));
		placeFields.add(new VariableRollObject<String>(95, 100, "Venusian"));
		
		factionFields.add(new VariableRollObject<String>(1,  6,  "Anarchist"));
		factionFields.add(new VariableRollObject<String>(7,  10, "Argonaut"));
		factionFields.add(new VariableRollObject<String>(11, 15, "Barsoomian"));
		factionFields.add(new VariableRollObject<String>(16, 17, "Belter"));
		factionFields.add(new VariableRollObject<String>(18, 19, "Bioconservative"));
		factionFields.add(new VariableRollObject<String>(20, 24, "Brinker"));
		factionFields.add(new VariableRollObject<String>(25, 31, "Criminal"));
		factionFields.add(new VariableRollObject<String>(32, 33, "Exhuman"));
		factionFields.add(new VariableRollObject<String>(34, 37, "Extropian"));
		factionFields.add(new VariableRollObject<String>(38, "Factors"));
		factionFields.add(new VariableRollObject<String>(39, 42, "Firewall"));
		factionFields.add(new VariableRollObject<String>(43, 51, "Hypercorp"));
		factionFields.add(new VariableRollObject<String>(52, 55, "Jovian"));
		factionFields.add(new VariableRollObject<String>(56, 61, "Lunar"));
		factionFields.add(new VariableRollObject<String>(62, 64, "Mercurial"));
		factionFields.add(new VariableRollObject<String>(65, "Nano-Ecologist"));
		factionFields.add(new VariableRollObject<String>(66, 67, "Orbital"));
		factionFields.add(new VariableRollObject<String>(68, "Out'ster"));
		factionFields.add(new VariableRollObject<String>(69, "Precautionist"));
		factionFields.add(new VariableRollObject<String>(70, "Preservationist"));
		factionFields.add(new VariableRollObject<String>(71, 74, "Reclaimer"));
		factionFields.add(new VariableRollObject<String>(75, 77, "Ringer"));
		factionFields.add(new VariableRollObject<String>(78, 80, "Sapient"));
		factionFields.add(new VariableRollObject<String>(81, 84, "Scum"));
		factionFields.add(new VariableRollObject<String>(85, "Sifter"));
		factionFields.add(new VariableRollObject<String>(86, "Skimmer"));
		factionFields.add(new VariableRollObject<String>(87, 90, "Socialite"));
		factionFields.add(new VariableRollObject<String>(91, "Solarian"));
		factionFields.add(new VariableRollObject<String>(92, 96, "Titanian"));
		factionFields.add(new VariableRollObject<String>(97, 100, "Ultimate"));
		
		interestFields.add(new VariableRollObject<String>(1,  2,  "Alien Relics"));
		interestFields.add(new VariableRollObject<String>(3,  4,  "Ancient Sports"));
		interestFields.add(new VariableRollObject<String>(5,  6,  "Art"));
		interestFields.add(new VariableRollObject<String>(7, "Wine"));
		interestFields.add(new VariableRollObject<String>(8, "Beers"));
		interestFields.add(new VariableRollObject<String>(9, "Red Markets"));
		interestFields.add(new VariableRollObject<String>(10, "Black Markets"));
		interestFields.add(new VariableRollObject<String>(11, 12, "Blogs"));
		interestFields.add(new VariableRollObject<String>(13, 14, "Celebrity Gossip"));
		interestFields.add(new VariableRollObject<String>(15, 16, "Conspiracies"));
		interestFields.add(new VariableRollObject<String>(17, "Cultural Trends"));
		interestFields.add(new VariableRollObject<String>(18, "Cultural Memes"));
		interestFields.add(new VariableRollObject<String>(19, 20, "Cutting-Edge Technology"));
		interestFields.add(new VariableRollObject<String>(21, 22, "Drug Dealers"));
		interestFields.add(new VariableRollObject<String>(23, 24, "Drugs/Petals/Narcoalgorithms"));
		interestFields.add(new VariableRollObject<String>(25, "Markets"));
		interestFields.add(new VariableRollObject<String>(26, "Economics"));
		interestFields.add(new VariableRollObject<String>(27, 28, "Exoplanets"));
		interestFields.add(new VariableRollObject<String>(29, 30, "Food"));
		interestFields.add(new VariableRollObject<String>(31, 32, "Gambling"));
		interestFields.add(new VariableRollObject<String>(33, 34, "Gangs"));
		interestFields.add(new VariableRollObject<String>(35, 36, "Gatecrashing"));
		interestFields.add(new VariableRollObject<String>(37, 38, "Groups"));
		interestFields.add(new VariableRollObject<String>(39, 40, "Habitats"));
		interestFields.add(new VariableRollObject<String>(41, 42, "History"));
		interestFields.add(new VariableRollObject<String>(43, 44, "Law"));
		interestFields.add(new VariableRollObject<String>(45, 46, "Literature"));
		interestFields.add(new VariableRollObject<String>(47, 48, "MARGs"));
		interestFields.add(new VariableRollObject<String>(49, 50, "Martial Arts"));
		interestFields.add(new VariableRollObject<String>(51, 52, " Mesh Forums"));
		interestFields.add(new VariableRollObject<String>(53, 54, "Morphs"));
		interestFields.add(new VariableRollObject<String>(55, 56, "Music"));
		interestFields.add(new VariableRollObject<String>(57, 58, "Nanofab Designs"));
		interestFields.add(new VariableRollObject<String>(59, 60, "Nightclubs"));
		interestFields.add(new VariableRollObject<String>(61, 62, "Old-Earth Nation-States"));
		interestFields.add(new VariableRollObject<String>(63, 64, "Old-Earth Trivia"));
		interestFields.add(new VariableRollObject<String>(65, 66, "Old-Earth Relics"));
		interestFields.add(new VariableRollObject<String>(67, 68, "Pandora Gates"));
		interestFields.add(new VariableRollObject<String>(69, 70, " Places of Interest"));
		interestFields.add(new VariableRollObject<String>(71, 72, "Politics"));
		interestFields.add(new VariableRollObject<String>(73, 74, "Pornography"));
		interestFields.add(new VariableRollObject<String>(75, "Uplift Rights"));
		interestFields.add(new VariableRollObject<String>(76, "AGI Rights"));
		interestFields.add(new VariableRollObject<String>(77, 78, "Robot Models"));
		interestFields.add(new VariableRollObject<String>(79, 80, "Rumors"));
		interestFields.add(new VariableRollObject<String>(81, 82, "Science Fiction"));
		interestFields.add(new VariableRollObject<String>(83, 84, "Smart Animals"));
		interestFields.add(new VariableRollObject<String>(85, 86, "Spaceship Models"));
		interestFields.add(new VariableRollObject<String>(87, 88, "Sports"));
		interestFields.add(new VariableRollObject<String>(89, 90, "TITANs"));
		interestFields.add(new VariableRollObject<String>(91, 92, "Trivia"));
		interestFields.add(new VariableRollObject<String>(93, 94, "Vehicle Models"));
		interestFields.add(new VariableRollObject<String>(95, 96, "Weapons"));
		interestFields.add(new VariableRollObject<String>(97, 98, "X-Casters"));
		interestFields.add(new VariableRollObject<String>(99, 100, "XP"));
		
		
		languageFields.add(new VariableRollObject<String>(1,  6,  "Arabic"));
		languageFields.add(new VariableRollObject<String>(7,  9,  "Bengali"));
		languageFields.add(new VariableRollObject<String>(10, 14, "Cantonese/Yue"));
		languageFields.add(new VariableRollObject<String>(15, 15, "Dutch"));
		languageFields.add(new VariableRollObject<String>(16, 24, "English"));
		languageFields.add(new VariableRollObject<String>(25, 27, "Farsi/Persian"));
		languageFields.add(new VariableRollObject<String>(28, 31, "French"));
		languageFields.add(new VariableRollObject<String>(32, 35, "German"));
		languageFields.add(new VariableRollObject<String>(36, 41, "Hindi"));
		languageFields.add(new VariableRollObject<String>(42, 42, "Italian"));
		languageFields.add(new VariableRollObject<String>(43, 47, "Japanese"));
		languageFields.add(new VariableRollObject<String>(48, 51, "Javanese"));
		languageFields.add(new VariableRollObject<String>(52, 53, "Korean"));
		languageFields.add(new VariableRollObject<String>(54, 62, "Mandarin"));
		languageFields.add(new VariableRollObject<String>(63, 63, "Polish"));
		languageFields.add(new VariableRollObject<String>(64, 68, "Portuguese"));
		languageFields.add(new VariableRollObject<String>(69, 71, "Punjabi"));
		languageFields.add(new VariableRollObject<String>(72, 76, "Russian"));
		languageFields.add(new VariableRollObject<String>(77, 78, "Skandinaviska")); 
		languageFields.add(new VariableRollObject<String>(79, 84, "Spanish"));
		languageFields.add(new VariableRollObject<String>(85, 85, "Swedish"));
		languageFields.add(new VariableRollObject<String>(86, 87, "Tamil"));
		languageFields.add(new VariableRollObject<String>(88, 89, "Turkish"));
		languageFields.add(new VariableRollObject<String>(90, 92, "Urdu"));
		languageFields.add(new VariableRollObject<String>(93, 94, "Vietnamese"));
		languageFields.add(new VariableRollObject<String>(95, 98, "Wu"));
		languageFields.add(new VariableRollObject<String>(99, 100, "Other"));
		
		medicineFields.add(new VariableRollObject<String>(1,  8,  "Biosculpting"));
		medicineFields.add(new VariableRollObject<String>(9,  13, "Exotic Biomorphs"));
		medicineFields.add(new VariableRollObject<String>(14, 21, "Gene Therapy"));
		medicineFields.add(new VariableRollObject<String>(22, 34, "General Practice"));
		medicineFields.add(new VariableRollObject<String>(35, 42, "Implant Surgery"));
		medicineFields.add(new VariableRollObject<String>(43, 52, "Nanomedicine"));
		medicineFields.add(new VariableRollObject<String>(53, 65, "Paramedic"));
		medicineFields.add(new VariableRollObject<String>(66, 70, "Pods"));
		medicineFields.add(new VariableRollObject<String>(71, 75, "Psychiatry"));
		medicineFields.add(new VariableRollObject<String>(76, 80, "Remote Surgery"));
		medicineFields.add(new VariableRollObject<String>(81, 90, "Trauma Surgery"));
		medicineFields.add(new VariableRollObject<String>(91, 95, "Uplifts"));
		medicineFields.add(new VariableRollObject<String>(96, 100, "Veterinary"));
		
		
		networkFields.add(new VariableRollObject<String>(1,  20, "Autonomists"));
		networkFields.add(new VariableRollObject<String>(21, 34, "Criminals"));
		networkFields.add(new VariableRollObject<String>(35, 44, "Ecologists"));
		networkFields.add(new VariableRollObject<String>(45, 54, "Firewall"));
		networkFields.add(new VariableRollObject<String>(55, 72, "Hypercorps"));
		networkFields.add(new VariableRollObject<String>(73, 86, "Media"));
		networkFields.add(new VariableRollObject<String>(87, 100, "Scientists"));
		
		pilotFields.add(new VariableRollObject<String>(1,  20, "Aircraft"));
		pilotFields.add(new VariableRollObject<String>(21, 40, "Anthroform"));
		pilotFields.add(new VariableRollObject<String>(41, 45, "Exotic Vehicle"));
		pilotFields.add(new VariableRollObject<String>(46, 70, "Groundcraft"));
		pilotFields.add(new VariableRollObject<String>(71, 95, "Spacecraft"));
		pilotFields.add(new VariableRollObject<String>(96, 100, "Watercraft"));
		
		professionFields.add(new VariableRollObject<String>(1,  2,  "Accounting"));
		professionFields.add(new VariableRollObject<String>(3,  4,  "Administration"));
		professionFields.add(new VariableRollObject<String>(5,  6,  "Appraisal"));
		professionFields.add(new VariableRollObject<String>(7,  8,  "Artisan"));
		professionFields.add(new VariableRollObject<String>(9, "Asteroid Mining"));
		professionFields.add(new VariableRollObject<String>(10, "Prospecting"));
		professionFields.add(new VariableRollObject<String>(11, 12, "Banking"));
		professionFields.add(new VariableRollObject<String>(13, 14, "Biodesign"));
		professionFields.add(new VariableRollObject<String>(15, 16, "Bodyguarding"));
		professionFields.add(new VariableRollObject<String>(17, 18, "Cool Hunting"));
		professionFields.add(new VariableRollObject<String>(19, 20, "Con Schemes"));
		professionFields.add(new VariableRollObject<String>(21, 22, "Culture Jamming"));
		professionFields.add(new VariableRollObject<String>(23, 24, "Customs Procedures"));
		professionFields.add(new VariableRollObject<String>(25, 26, "Darknet Operations"));
		professionFields.add(new VariableRollObject<String>(27, 28, "Distribution"));
		professionFields.add(new VariableRollObject<String>(29, 30, "Ego Hunting"));
		professionFields.add(new VariableRollObject<String>(31, 32, "Escorting"));
		professionFields.add(new VariableRollObject<String>(33, 34, "Excavation"));
		professionFields.add(new VariableRollObject<String>(35, 36, "First Contact"));
		professionFields.add(new VariableRollObject<String>(37, 38, "Forensics"));
		professionFields.add(new VariableRollObject<String>(39, 40, "Gatecrashing"));
		professionFields.add(new VariableRollObject<String>(41, 42, "Habitat Operations"));
		professionFields.add(new VariableRollObject<String>(43, 44, "Hacktivism"));
		professionFields.add(new VariableRollObject<String>(45, 46, "Info Brokerage"));
		professionFields.add(new VariableRollObject<String>(47, 48, "Instruction"));
		professionFields.add(new VariableRollObject<String>(49, 50, "Journalism"));
		professionFields.add(new VariableRollObject<String>(51, 52, "Lab Technician"));
		professionFields.add(new VariableRollObject<String>(53, 54, "Law"));
		professionFields.add(new VariableRollObject<String>(55, 56, "Medical Care"));
		professionFields.add(new VariableRollObject<String>(57, 58, "Mesh Networking"));
		professionFields.add(new VariableRollObject<String>(59, 60, "Mesh Security"));
		professionFields.add(new VariableRollObject<String>(61, 62, "Military Operations"));
		professionFields.add(new VariableRollObject<String>(63, 64, "Minifacturing"));
		professionFields.add(new VariableRollObject<String>(65, 66, "Mining"));
		professionFields.add(new VariableRollObject<String>(67, 68, "Morph Brokerage"));
		professionFields.add(new VariableRollObject<String>(69, 70, "Police Procedures"));
		professionFields.add(new VariableRollObject<String>(71, 72, "Protection Rackets"));
		professionFields.add(new VariableRollObject<String>(73, 74, "Psychotherapy"));
		professionFields.add(new VariableRollObject<String>(75, 76, "Scavenging"));
		professionFields.add(new VariableRollObject<String>(77, 78, "Salvage Ops"));
		professionFields.add(new VariableRollObject<String>(79, 80, "Security Operations"));
		professionFields.add(new VariableRollObject<String>(81, 82, "Smuggling Tricks"));
		professionFields.add(new VariableRollObject<String>(83, 84, "Social Engineering"));
		professionFields.add(new VariableRollObject<String>(85, 86, "Spacecraft Systems"));
		professionFields.add(new VariableRollObject<String>(87, 88, "Spaceship Crew"));
		professionFields.add(new VariableRollObject<String>(89, "Spin Control"));
		professionFields.add(new VariableRollObject<String>(90, "Public Relations"));
		professionFields.add(new VariableRollObject<String>(91, 92, "Squad Tactics"));
		professionFields.add(new VariableRollObject<String>(93, 94, "Surveying"));
		professionFields.add(new VariableRollObject<String>(95, 96, "Terraforming"));
		professionFields.add(new VariableRollObject<String>(97, 98, "Viral Marketing"));
		professionFields.add(new VariableRollObject<String>(99, 100, "XP Production"));
	}
}
