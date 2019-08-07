package me.ci.commands;

import java.io.File;
import me.ci.user.User;
import net.whg.awgenshell.arg.ArgumentValue;
import net.whg.awgenshell.exec.CommandHandler;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.util.CommandResult;

public class ShowMeCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	@Override
	public String getName()
	{
		return "showme";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		User user = (User) env.getCommandSender();

		if (args.length == 0)
		{
			File[] files = getImages();

			if (files.length == 0)
			{
				user.sendMessage("No files in database! :cryingchicken:");
				return CommandResult.ERROR;
			}

			int randomIndex = (int) (Math.random() * files.length);
			System.out.println("Uploading file: " + files[randomIndex]);

			user.sendMessage("Here's a random animal picture for you! :happychicken:");
			user.sendFile(files[randomIndex]);

			return CommandResult.SUCCESS;
		}

		String sub = args[0].getValue();

		if (sub.equals("list"))
		{
			if (args.length != 1)
			{
				user.sendMessage("Unknown number of arguments!");
				return CommandResult.ERROR;
			}

			File[] files = getImages();
			StringBuilder sb = new StringBuilder();

			sb.append("Currently uploaded files:");
			sb.append(" (").append(files.length).append(")");
			sb.append("\n```");
			for (File file : files)
				sb.append(file.getName()).append("\n");
			sb.append("```");

			user.sendMessage(sb.toString());

			return CommandResult.SUCCESS;
		}

		if (sub.equals("remove"))
		{
			if (args.length != 2)
			{
				user.sendMessage("Unknown number of arguments!");
				return CommandResult.ERROR;
			}

			File[] files = getImages();
			String toDelete = args[1].getValue();

			for (File file : files)
			{
				if (file.getName().equals(toDelete))
				{
					file.delete();
					user.sendMessage("Image removed from database.");
					return CommandResult.SUCCESS;
				}
			}

			user.sendMessage("Image not found!");
			return CommandResult.ERROR;
		}

		if (sub.equals("rename"))
		{
			if (args.length != 3)
			{
				user.sendMessage("Unknown number of arguments!");
				return CommandResult.ERROR;
			}

			File[] files = getImages();
			String toRename = args[1].getValue();
			String name = args[2].getValue();

			for (File file : files)
			{
				if (file.getName().equals(toRename))
				{
					File newName = new File(file.getParentFile(), name);
					file.renameTo(newName);

					user.sendMessage("Image has been renamed.");
					return CommandResult.SUCCESS;
				}
			}

			user.sendMessage("Image not found!");
			return CommandResult.ERROR;
		}

		if (args.length != 1)
		{
			user.sendMessage("Unknown subcommand!");
			return CommandResult.ERROR;
		}

		File[] files = getImages();
		String toShow = args[0].getValue();

		for (File file : files)
		{
			if (file.getName().equals(toShow))
			{
				user.sendMessage("Here you go!");
				user.sendFile(file);
				return CommandResult.SUCCESS;
			}
		}

		user.sendMessage("Image not found!");
		return CommandResult.ERROR;
	}

	private File[] getImages()
	{
		String dir = System.getProperty("user.dir");
		File imageFolder = new File(dir, "pictures");
		return imageFolder.listFiles();
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
