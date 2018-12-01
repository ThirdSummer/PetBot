package me.ci;

import java.io.File;

import me.ci.DiscordBridge.DiscordChannelBridge;

public class SentCommand
{
	private DiscordChannelBridge _channel;
	private String _command;
	private String[] _args;
	
	public SentCommand(DiscordChannelBridge channel, String command, String[] args)
	{
		_channel = channel;
		_command = command;
		_args = args;
	}
	
	public void sendMessage(String message)
	{
		_channel.sendMessage(message);
	}
	
	public String getCommand()
	{
		return _command;
	}
	
	public String[] getArguments()
	{
		return _args;
	}
	
	public void uploadFile(File file)
	{
		_channel.sendFile(file);
	}
}
