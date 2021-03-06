package me.Haeseke1.Alliances.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Haeseke1.Alliances.APlayer.APlayerEvents;
import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Alliance.Bonus_Timer;
import me.Haeseke1.Alliances.Alliance.Event.AllianceMemberDamage;
import me.Haeseke1.Alliances.Alliance.Type.Caith_Sith;
import me.Haeseke1.Alliances.Alliance.Type.Pooka;
import me.Haeseke1.Alliances.Alliance.Type.Salamander;
import me.Haeseke1.Alliances.Alliance.Type.Spriggan;
import me.Haeseke1.Alliances.Arena.ArenaEvents;
import me.Haeseke1.Alliances.Arena.ArenaManager;
import me.Haeseke1.Alliances.Arena.Commands.ArenaCommand;
import me.Haeseke1.Alliances.Auctions.Auction;
import me.Haeseke1.Alliances.Auctions.AuctionPlayer;
import me.Haeseke1.Alliances.Auctions.Commands.AuctionCommand;
import me.Haeseke1.Alliances.Auctions.Commands.Rewards;
import me.Haeseke1.Alliances.Auctions.Events.InventoryClick;
import me.Haeseke1.Alliances.Auctions.Events.RewardJoin;
import me.Haeseke1.Alliances.Buildings.BuildingListener;
import me.Haeseke1.Alliances.Buildings.BuildingManager;
import me.Haeseke1.Alliances.Buildings.Builder.BlockPlace;
import me.Haeseke1.Alliances.Buildings.Builder.BuilderManager;
import me.Haeseke1.Alliances.Buildings.Commands.BuildingC;
import me.Haeseke1.Alliances.Buildings.Type.Storage.AlchemyListener;
import me.Haeseke1.Alliances.Buildings.Type.Storage.StorageListener;
import me.Haeseke1.Alliances.Challenge.ChallengeManager;
import me.Haeseke1.Alliances.Challenge.Commands.mainChallenges;
import me.Haeseke1.Alliances.Challenge.Type.Block_Breaking;
import me.Haeseke1.Alliances.Challenge.Type.Block_Placing;
import me.Haeseke1.Alliances.Challenge.Type.Distance_Travel;
import me.Haeseke1.Alliances.Challenge.Type.Enchanting;
import me.Haeseke1.Alliances.Challenge.Type.Fishing;
import me.Haeseke1.Alliances.Challenge.Type.Lost_Hearts;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Count;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Enderman;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Skeleton;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Time;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Wither;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Zombie;
import me.Haeseke1.Alliances.Challenge.Type.Player_Kill;
import me.Haeseke1.Alliances.Challenge.Type.Time_On;
import me.Haeseke1.Alliances.Chat.ChatEvent;
import me.Haeseke1.Alliances.Commands.Alli;
import me.Haeseke1.Alliances.Commands.Create.InventoryEvents;
import me.Haeseke1.Alliances.Crates.CrateManager;
import me.Haeseke1.Alliances.Crates.CratesEvents;
import me.Haeseke1.Alliances.Crates.Commands.CrateC;
import me.Haeseke1.Alliances.CustomEntity.CustomEntityVillager;
import me.Haeseke1.Alliances.CustomEntity.Blaze.BlazeLV1;
import me.Haeseke1.Alliances.CustomEntity.Blaze.BlazeLV2;
import me.Haeseke1.Alliances.CustomEntity.Blaze.BlazeLV3;
import me.Haeseke1.Alliances.CustomEntity.Blaze.BlazeLV4;
import me.Haeseke1.Alliances.CustomEntity.Blaze.BlazeLV5;
import me.Haeseke1.Alliances.CustomEntity.Creeper.CreeperLV1;
import me.Haeseke1.Alliances.CustomEntity.Creeper.CreeperLV2;
import me.Haeseke1.Alliances.CustomEntity.Creeper.CreeperLV3;
import me.Haeseke1.Alliances.CustomEntity.Creeper.CreeperLV4;
import me.Haeseke1.Alliances.CustomEntity.Creeper.CreeperLV5;
import me.Haeseke1.Alliances.CustomEntity.Enderman.EndermanLV1;
import me.Haeseke1.Alliances.CustomEntity.Enderman.EndermanLV2;
import me.Haeseke1.Alliances.CustomEntity.Enderman.EndermanLV3;
import me.Haeseke1.Alliances.CustomEntity.Enderman.EndermanLV4;
import me.Haeseke1.Alliances.CustomEntity.Enderman.EndermanLV5;
import me.Haeseke1.Alliances.CustomEntity.Skeleton.SkeletonLV1;
import me.Haeseke1.Alliances.CustomEntity.Skeleton.SkeletonLV2;
import me.Haeseke1.Alliances.CustomEntity.Skeleton.SkeletonLV3;
import me.Haeseke1.Alliances.CustomEntity.Skeleton.SkeletonLV4;
import me.Haeseke1.Alliances.CustomEntity.Skeleton.SkeletonLV5;
import me.Haeseke1.Alliances.CustomEntity.Spider.SpiderLV1;
import me.Haeseke1.Alliances.CustomEntity.Spider.SpiderLV2;
import me.Haeseke1.Alliances.CustomEntity.Spider.SpiderLV3;
import me.Haeseke1.Alliances.CustomEntity.Spider.SpiderLV4;
import me.Haeseke1.Alliances.CustomEntity.Spider.SpiderLV5;
import me.Haeseke1.Alliances.CustomEntity.Wither.WitherLV1;
import me.Haeseke1.Alliances.CustomEntity.Wither.WitherLV2;
import me.Haeseke1.Alliances.CustomEntity.Wither.WitherLV3;
import me.Haeseke1.Alliances.CustomEntity.Wither.WitherLV4;
import me.Haeseke1.Alliances.CustomEntity.Wither.WitherLV5;
import me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton.Wither_SkeletonLV1;
import me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton.Wither_SkeletonLV2;
import me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton.Wither_SkeletonLV3;
import me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton.Wither_SkeletonLV4;
import me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton.Wither_SkeletonLV5;
import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV1;
import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV2;
import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV3;
import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV4;
import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV5;
import me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman.Zombie_PigmanLV1;
import me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman.Zombie_PigmanLV2;
import me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman.Zombie_PigmanLV3;
import me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman.Zombie_PigmanLV4;
import me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman.Zombie_PigmanLV5;
import me.Haeseke1.Alliances.Economy.Commands.CoinC;
import me.Haeseke1.Alliances.Economy.Events.BlockCommand;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyItemStackException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Exceptions.InvalidConfigTypeException;
import me.Haeseke1.Alliances.Item.Buildings.Storage.Storage_Level;
import me.Haeseke1.Alliances.Item.Commands.Item;
import me.Haeseke1.Alliances.Item.Totems.HealingTotem;
import me.Haeseke1.Alliances.Item.Totems.Events.DamageTotem;
import me.Haeseke1.Alliances.Item.Totems.Scheduler.Checker;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Arrow_Tank;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Creeper_Armor;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Drunk;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Fire_Imp;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Flame_Of_Hell;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Golden;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Legend_Of_Zeus;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Tank;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Void_Armor;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Schedulers.CheckCooldowns;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Blade_Of_Zeus;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Double_Strike;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Excalibur;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Fatal_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Heaven_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Karma_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Mob_Slayer;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Night_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Soul_Stealer;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Speed_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Swift_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Warrior_Sword;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Wither_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Commands.Wand;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Events.RightClickWand;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Scheduler.Healing;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Scheduler.ManaRegen;
import me.Haeseke1.Alliances.LeaderBoard.Head_Board;
import me.Haeseke1.Alliances.LeaderBoard.LeaderBoard;
import me.Haeseke1.Alliances.LeaderBoard.LeaderBoard_Timer;
import me.Haeseke1.Alliances.LeaderBoard.Commands.LeaderboardC;
import me.Haeseke1.Alliances.Mounts.MountsManager;
import me.Haeseke1.Alliances.Mounts.Commands.MountCommand;
import me.Haeseke1.Alliances.Mounts.Events.Death;
import me.Haeseke1.Alliances.Mounts.Events.Leave;
import me.Haeseke1.Alliances.Mounts.Events.RightClickMount;
import me.Haeseke1.Alliances.Mounts.Scheduler.Updater;
import me.Haeseke1.Alliances.Outpost.OutpostEvents;
import me.Haeseke1.Alliances.Outpost.OutpostManager;
import me.Haeseke1.Alliances.Outpost.Timer;
import me.Haeseke1.Alliances.Outpost.Commands.Outpost;
import me.Haeseke1.Alliances.PVE.PVEManager;
import me.Haeseke1.Alliances.PVE.Commands.PVEC;
import me.Haeseke1.Alliances.PVE.Events.BlockBreak_Place;
import me.Haeseke1.Alliances.PVE.Events.EntityHit;
import me.Haeseke1.Alliances.PVE.Events.PlayerClickInventory;
import me.Haeseke1.Alliances.PVE.Events.PlayerCommand;
import me.Haeseke1.Alliances.PVE.Events.PlayerMove;
import me.Haeseke1.Alliances.PVE.Events.PlayerQuit;
import me.Haeseke1.Alliances.PVE.Schedulers.Arena_Scheduler;
import me.Haeseke1.Alliances.PVE.Schedulers.PVE_Scheduler;
import me.Haeseke1.Alliances.Portals.Portal;
import me.Haeseke1.Alliances.Portals.Commands.PortalCommand;
import me.Haeseke1.Alliances.Portals.Events.PortalMoveEvent;
import me.Haeseke1.Alliances.ScoreBoard.Update.Counter;
import me.Haeseke1.Alliances.Shop.ShopEvents;
import me.Haeseke1.Alliances.Shop.ShopManager;
import me.Haeseke1.Alliances.Shop.Commands.ShopC;
import me.Haeseke1.Alliances.Teleport.Events.checkTPACommand;
import me.Haeseke1.Alliances.Town.TownEvents;
import me.Haeseke1.Alliances.Town.TownManager;
import me.Haeseke1.Alliances.Town.Commands.TownC;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Vote.VoteManager;
import me.Haeseke1.Alliances.Vote.VotePlayer;
import me.Haeseke1.Alliances.Vote.Commands.VoteCommand;
import me.Haeseke1.Alliances.Vote.Events.VoteEvent;
import me.Haeseke1.Alliances.WorldGuard.Commands.WorldGuardCommand;
import me.Haeseke1.Alliances.WorldGuard.Events.Blocks;
import me.Haeseke1.Alliances.WorldGuard.Events.FoodChange;
import me.Haeseke1.Alliances.WorldGuard.Events.Moving;
import me.Haeseke1.Alliances.WorldGuard.Events.Slaying;
import me.Haeseke1.Alliances.WorldGuard.Regions.Region;
import me.Haeseke1.Alliances.WorldGuard.Regions.Settings;
import me.Haeseke1.Alliances.regionSelect.regionSelect;
import me.Haeseke1.Alliances.regionSelect.Commands.Particle_Timer;
import me.Haeseke1.Alliances.regionSelect.Commands.region;
import net.minecraft.server.v1_8_R2.EntityBlaze;
import net.minecraft.server.v1_8_R2.EntityCreeper;
import net.minecraft.server.v1_8_R2.EntityEnderman;
import net.minecraft.server.v1_8_R2.EntityPigZombie;
import net.minecraft.server.v1_8_R2.EntitySkeleton;
import net.minecraft.server.v1_8_R2.EntitySpider;
import net.minecraft.server.v1_8_R2.EntityVillager;
import net.minecraft.server.v1_8_R2.EntityWither;
import net.minecraft.server.v1_8_R2.EntityZombie;

public class Main extends JavaPlugin {

	public static FileConfiguration config;
	public static String cmdlogo;
	public static PluginManager pm = Bukkit.getPluginManager();

	public static HashMap<String, FileConfiguration> configFiles = new HashMap<String, FileConfiguration>();
	public static HashMap<String, File> configFile = new HashMap<String, File>();
	public static List<Alliance> alliances = new ArrayList<Alliance>();

	public static FileConfiguration coinsConfig;
	public static FileConfiguration alliancesConfig;
	public static FileConfiguration outpostConfig;
	public static FileConfiguration challengeConfig;
	public static FileConfiguration shopConfig;
	public static FileConfiguration messageConfig;
	public static FileConfiguration arenaConfig;
	public static FileConfiguration settingsConfig;
	public static FileConfiguration PVEConfig;
	public static FileConfiguration BuildingConfig;
	public static FileConfiguration CratesConfig;
	public static FileConfiguration RewardsConfig;
	public static FileConfiguration AuctionConfig;
	public static FileConfiguration PortalsConfig;
	public static FileConfiguration LeaderboardConfig;
	public static FileConfiguration VoteConfig;
	public static FileConfiguration StatsConfig;
	public static FileConfiguration WorldGuardConfig;


	public static Main plugin;

	@SuppressWarnings("static-access")
	@Override
	public void onEnable() {
		this.config = getConfig();
		this.plugin = this;
		Config.registerConfigFile(this);
		try {
			createConfigs();
		} catch (IOException e) {
			MessageManager.sendAlertMessage("There was a problem with loading in the config file");
			e.printStackTrace();
			return;
		} catch (InvalidConfigTypeException icte) {
			icte.printStackTrace();
			MessageManager.sendAlertMessage(
					"There was a problem in the code. Ask a dev for more information or download an earlier version of this plugin");
			return;
		}
		Settings.startUp();
		registerCommands();
		registerEvents();
		registerSchedulers();
		registerCustomEntitys();
		
		MessageManager.sendRemarkMessage("The plugin is doing fine... *-* The cake is a lie *-*");
		MessageManager.sendAlertMessage("The plugin is doing fine... *-* The cake is a lie *-*");
		MessageManager.sendInfoMessage("The plugin is doing fine... *-* The cake is a lie *-*");
	}

	@Override
	public void onDisable() {
		for(Player player : Bukkit.getOnlinePlayers()){
			player.closeInventory();
		}
	    HealingTotem.removeAllTotems();
		PVEManager.disablePVE();
		MountsManager.despawnMounts();
		Config.saveConfigFile(this);
		saveAllCustomConfigs();
		ShopManager.despawnVendors();
		SQL.forceCloseConnection();
		MessageManager.sendAlertMessage("The plugin has been shutted down! *-* The cake wasn't a lie thought *-*");
	}

	public void registerEvents() {
		pm.registerEvents(new InventoryEvents(), this);
		pm.registerEvents(new me.Haeseke1.Alliances.Commands.Join.InventoryEvents(), this);
		pm.registerEvents(new me.Haeseke1.Alliances.Challenge.Commands.Player.InventoryEvents(), this);
		pm.registerEvents(new OutpostEvents(), this);
		pm.registerEvents(new regionSelect(), this);
		pm.registerEvents(new ShopEvents(), this);
		pm.registerEvents(new ChatEvent(), this);
		pm.registerEvents(new TownEvents(), this);
		pm.registerEvents(new APlayerEvents(), this);
		/*
		 * Challenges
		 */
		pm.registerEvents(new Block_Breaking(), this);
		pm.registerEvents(new Block_Placing(), this);
		pm.registerEvents(new Distance_Travel(), this);
		pm.registerEvents(new Enchanting(), this);
		pm.registerEvents(new Fishing(), this);
		pm.registerEvents(new Lost_Hearts(), this);
		pm.registerEvents(new Mob_Killing_Count(), this);
		pm.registerEvents(new Mob_Killing_Enderman(), this);
		pm.registerEvents(new Mob_Killing_Skeleton(), this);
		pm.registerEvents(new Mob_Killing_Time(), this);
		pm.registerEvents(new Mob_Killing_Wither(), this);
		pm.registerEvents(new Mob_Killing_Zombie(), this);
		pm.registerEvents(new Player_Kill(), this);
		/*
		 * Arena events
		 */
		pm.registerEvents(new ArenaEvents(), this);
		/*
		 * Coins events
		 */
		pm.registerEvents(new BlockCommand(), this);
		/*
		 * Mounts events
		 */
		pm.registerEvents(new Death(), this);
		pm.registerEvents(new RightClickMount(), this);
		pm.registerEvents(new Leave(), this);
		/*
		 * Swords events
		 */
		pm.registerEvents(new Warrior_Sword(), this);
		pm.registerEvents(new Night_Blade(), this);
		pm.registerEvents(new Wither_Blade(), this);
		pm.registerEvents(new Fatal_Blade(), this);
		pm.registerEvents(new Soul_Stealer(), this);
		pm.registerEvents(new Swift_Blade(), this);
		pm.registerEvents(new Blade_Of_Zeus(), this);
		pm.registerEvents(new Karma_Blade(), this);
		pm.registerEvents(new Double_Strike(), this);
		pm.registerEvents(new Speed_Blade(), this);
		pm.registerEvents(new Heaven_Blade(), this);
		pm.registerEvents(new Mob_Slayer(), this);
		pm.registerEvents(new Golden(), this);
		pm.registerEvents(new Excalibur(), this);
		pm.registerEvents(new Creeper_Armor(), this);
		
		pm.registerEvents(new Arrow_Tank(), this);
		pm.registerEvents(new Tank(), this);
		pm.registerEvents(new Legend_Of_Zeus(), this);
		pm.registerEvents(new Flame_Of_Hell(), this);
		pm.registerEvents(new Void_Armor(), this);
		pm.registerEvents(new Flame_Of_Hell(), this);
		pm.registerEvents(new Fire_Imp(), this);
		pm.registerEvents(new Drunk(), this);
		
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new BlockBreak_Place(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new EntityHit(), this);
		pm.registerEvents(new PlayerClickInventory(), this);
		pm.registerEvents(new PlayerCommand(), this);

		pm.registerEvents(new BlockPlace(), this);
		pm.registerEvents(new StorageListener(), this);
		pm.registerEvents(new BuildingListener(), this);
		pm.registerEvents(new checkTPACommand(), this);

		pm.registerEvents(new Storage_Level(), this);
		
		pm.registerEvents(new RightClickWand(), this);
		
		pm.registerEvents(new DamageTotem(), this);
		
		pm.registerEvents(new CratesEvents(), this);
		pm.registerEvents(new AlchemyListener(), this);
		
		pm.registerEvents(new Head_Board(), this);
		
		pm.registerEvents(new InventoryClick(), this);
		pm.registerEvents(new RewardJoin(), this);
		pm.registerEvents(new Caith_Sith(), this);
		pm.registerEvents(new Pooka(), this);
		pm.registerEvents(new Salamander(), this);
		pm.registerEvents(new Spriggan(), this);
		
		pm.registerEvents(new PortalMoveEvent(), this);
		
		pm.registerEvents(new VoteEvent(), this);
		
		pm.registerEvents(new AllianceMemberDamage(), this);
		
		pm.registerEvents(new Slaying(), this);
		pm.registerEvents(new Moving(), this);
		pm.registerEvents(new Blocks(), this);
		pm.registerEvents(new FoodChange(), this);
	}

	public void registerCommands() {
		getCommand("Alliances").setExecutor(new Alli());
		getCommand("Coin").setExecutor(new CoinC());
		getCommand("Shop").setExecutor(new ShopC());
		getCommand("Outpost").setExecutor(new Outpost());
		getCommand("terrain").setExecutor(new region());
		getCommand("Challenges").setExecutor(new mainChallenges());
		getCommand("Town").setExecutor(new TownC());
		getCommand("Arena").setExecutor(new ArenaCommand());
		getCommand("Mount").setExecutor(new MountCommand());
		getCommand("PVE").setExecutor(new PVEC());
		getCommand("building").setExecutor(new BuildingC());
		getCommand("items").setExecutor(new Item());
		getCommand("wand").setExecutor(new Wand());
		getCommand("crate").setExecutor(new CrateC());
		getCommand("auction").setExecutor(new AuctionCommand());
		getCommand("portal").setExecutor(new PortalCommand());
		getCommand("leaderboard").setExecutor(new LeaderboardC());

		getCommand("portal").setExecutor(new PortalCommand());
		getCommand("rewards").setExecutor(new Rewards());
		getCommand("voteset").setExecutor(new VoteCommand());
		getCommand("WorldGuard").setExecutor(new WorldGuardCommand());
	}

	public void registerCustomEntitys() {
		NMSUtil nmsUtil = new NMSUtil();
		nmsUtil.registerEntity("Vendor", 120, EntityVillager.class, CustomEntityVillager.class);
		nmsUtil.registerEntity("Zombie LV1", 54, EntityZombie.class, ZombieLV1.class);
		nmsUtil.registerEntity("Zombie LV2", 54, EntityZombie.class, ZombieLV2.class);
		nmsUtil.registerEntity("Zombie LV3", 54, EntityZombie.class, ZombieLV3.class);
		nmsUtil.registerEntity("Zombie LV4", 54, EntityZombie.class, ZombieLV4.class);
		nmsUtil.registerEntity("Zombie LV5", 54, EntityZombie.class, ZombieLV5.class);

		nmsUtil.registerEntity("Skeleton LV1", 51, EntitySkeleton.class, SkeletonLV1.class);
		nmsUtil.registerEntity("Skeleton LV2", 51, EntitySkeleton.class, SkeletonLV2.class);
		nmsUtil.registerEntity("Skeleton LV3", 51, EntitySkeleton.class, SkeletonLV3.class);
		nmsUtil.registerEntity("Skeleton LV4", 51, EntitySkeleton.class, SkeletonLV4.class);
		nmsUtil.registerEntity("Skeleton LV5", 51, EntitySkeleton.class, SkeletonLV5.class);

		nmsUtil.registerEntity("Blaze LV1", 61, EntityBlaze.class, BlazeLV1.class);
		nmsUtil.registerEntity("Blaze LV2", 61, EntityBlaze.class, BlazeLV2.class);
		nmsUtil.registerEntity("Blaze LV3", 61, EntityBlaze.class, BlazeLV3.class);
		nmsUtil.registerEntity("Blaze LV4", 61, EntityBlaze.class, BlazeLV4.class);
		nmsUtil.registerEntity("Blaze LV5", 61, EntityBlaze.class, BlazeLV5.class);

		nmsUtil.registerEntity("Creeper LV1", 50, EntityCreeper.class, CreeperLV1.class);
		nmsUtil.registerEntity("Creeper LV2", 50, EntityCreeper.class, CreeperLV2.class);
		nmsUtil.registerEntity("Creeper LV3", 50, EntityCreeper.class, CreeperLV3.class);
		nmsUtil.registerEntity("Creeper LV4", 50, EntityCreeper.class, CreeperLV4.class);
		nmsUtil.registerEntity("Creeper LV5", 50, EntityCreeper.class, CreeperLV5.class);

		nmsUtil.registerEntity("Enderman LV1", 58, EntityEnderman.class, EndermanLV1.class);
		nmsUtil.registerEntity("Enderman LV2", 58, EntityEnderman.class, EndermanLV2.class);
		nmsUtil.registerEntity("Enderman LV3", 58, EntityEnderman.class, EndermanLV3.class);
		nmsUtil.registerEntity("Enderman LV4", 58, EntityEnderman.class, EndermanLV4.class);
		nmsUtil.registerEntity("Enderman LV5", 58, EntityEnderman.class, EndermanLV5.class);

		nmsUtil.registerEntity("Spider LV1", 52, EntitySpider.class, SpiderLV1.class);
		nmsUtil.registerEntity("Spider LV2", 52, EntitySpider.class, SpiderLV2.class);
		nmsUtil.registerEntity("Spider LV3", 52, EntitySpider.class, SpiderLV3.class);
		nmsUtil.registerEntity("Spider LV4", 52, EntitySpider.class, SpiderLV4.class);
		nmsUtil.registerEntity("Spider LV5", 52, EntitySpider.class, SpiderLV5.class);

		nmsUtil.registerEntity("Wither LV1", 64, EntityWither.class, WitherLV1.class);
		nmsUtil.registerEntity("Wither LV2", 64, EntityWither.class, WitherLV2.class);
		nmsUtil.registerEntity("Wither LV3", 64, EntityWither.class, WitherLV3.class);
		nmsUtil.registerEntity("Wither LV4", 64, EntityWither.class, WitherLV4.class);
		nmsUtil.registerEntity("Wither LV5", 64, EntityWither.class, WitherLV5.class);

		nmsUtil.registerEntity("Wither_Skeleton LV1", 64, EntitySkeleton.class, Wither_SkeletonLV1.class);
		nmsUtil.registerEntity("Wither_Skeleton LV2", 64, EntitySkeleton.class, Wither_SkeletonLV2.class);
		nmsUtil.registerEntity("Wither_Skeleton LV3", 64, EntitySkeleton.class, Wither_SkeletonLV3.class);
		nmsUtil.registerEntity("Wither_Skeleton LV4", 64, EntitySkeleton.class, Wither_SkeletonLV4.class);
		nmsUtil.registerEntity("Wither_Skeleton LV5", 64, EntitySkeleton.class, Wither_SkeletonLV5.class);

		nmsUtil.registerEntity("Zombie_Pigman LV1", 57, EntityPigZombie.class, Zombie_PigmanLV1.class);
		nmsUtil.registerEntity("Zombie_Pigman LV2", 57, EntityPigZombie.class, Zombie_PigmanLV2.class);
		nmsUtil.registerEntity("Zombie_Pigman LV3", 57, EntityPigZombie.class, Zombie_PigmanLV3.class);
		nmsUtil.registerEntity("Zombie_Pigman LV4", 57, EntityPigZombie.class, Zombie_PigmanLV4.class);
		nmsUtil.registerEntity("Zombie_Pigman LV5", 57, EntityPigZombie.class, Zombie_PigmanLV5.class);

	}

	public void registerSchedulers() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Timer(), 20, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Time_On(), 20, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Mob_Killing_Time(), 20, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Particle_Timer(), 25, 25);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new me.Haeseke1.Alliances.Town.Commands.Particle_Timer(),
				25, 25);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Counter(), 0, 10);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Updater(), 0, 5);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new CheckCooldowns(), 0, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new PVE_Scheduler(), 20, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Arena_Scheduler(), 20, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ManaRegen(), 0L, 100);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Checker(), 0L, 1L);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Healing(), 0L, 50L);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Bonus_Timer(), 20, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LeaderBoard_Timer(), 20, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new AutoSave(), 800, 800);
		java.util.Timer timer = new java.util.Timer(); 
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 1);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		timer.schedule(new ChallengeManager(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
	}
	
	
	

	/*
	 * Creates all the needed configs of the code (JSON support not included)
	 */
	public static void createConfigs() throws IOException, InvalidConfigTypeException {
		coinsConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(), "coins.yml"), plugin);
		alliancesConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(), "alliances.yml"), plugin);
		outpostConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(), "outpost.yml"), plugin);
		challengeConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(), "challenges.yml"), plugin);
		shopConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(), "shop.yml"), plugin);
		arenaConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(), "arenas.yml"), plugin);
		settingsConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(), "settings.yml"), plugin);
		PVEConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(), "PVE.yml"), plugin);
		BuildingConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(), "buildings.yml"), plugin);
		CratesConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(), "crates.yml"), plugin);
		RewardsConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(),"rewards.yml"), plugin);
		AuctionConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(),"auction.yml"), plugin);
		PortalsConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(),"portals.yml"), plugin);
		LeaderboardConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(),"leaderboard.yml"), plugin);
		VoteConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(),"vote.yml"),plugin);
		StatsConfig = ConfigManager.getCustomConfig(new File(Main.plugin.getDataFolder(),"stats.yml"), plugin);
		WorldGuardConfig = ConfigManager.getCustomConfig(new File(plugin.getDataFolder(),"worldguard.yml"),plugin);
		try {
			ArenaManager.loadArena();
			VoteManager.loadVoteRewards();
			Portal.loadPortals();
		} catch (EmptyIntException | EmptyLocationException | EmptyStringException | EmptyItemStackException e) {
			e.printStackTrace();
		}
		CrateManager.registerCrate();
		AllianceManager.registerAlliance();
		TownManager.registerTowns();
		BuildingManager.registerBuildings();
		OutpostManager.registerOutpost();
		ChallengeManager.registerChallenges();
		ShopManager.registerShops();
		PVEManager.registerPVE();
		BuilderManager.registerBuilders();
		LeaderBoard.registerLeaderboard();
		VotePlayer.loadVotePlayers();
		APlayerManager.aPlayerStartUp();
		Auction.loadAuctions();
		AuctionPlayer.loadAuctionPlayers();
	}

	public static void saveAllCustomConfigs() {
		Region.saveRegions();
		AllianceManager.saveAlliance();
		CrateManager.saveCrate();
		OutpostManager.saveOutpost();
		ShopManager.saveShops();
		TownManager.saveTowns();
		PVEManager.savePVE();
		BuildingManager.saveBuildings();
		BuilderManager.saveBuilders();
		LeaderBoard.saveLeaderboard();
		VotePlayer.saveVotePlayers();
		VoteManager.saveVoteRewards();
        Portal.savePortals();
	    Auction.saveAuctions();
	    AuctionPlayer.saveAuctionPlayers();
		APlayerManager.aPlayerSave();
		for (Entry<String, FileConfiguration> entry : configFiles.entrySet()) {
			if (configFile.containsKey(entry.getKey())) {
				ConfigManager.saveCustomConfig(configFile.get(entry.getKey()), entry.getValue());
			} else {
				ConfigManager.saveCustomConfig(new File(plugin.getDataFolder(), entry.getKey()), entry.getValue());
			}

		}
	}
}
