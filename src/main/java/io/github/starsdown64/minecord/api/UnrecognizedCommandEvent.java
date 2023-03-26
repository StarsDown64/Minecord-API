package io.github.starsdown64.minecord.api;

import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Allows external plugins to handle commands sent to Minecord in Discord that are not part of Minecord's commands.
 * {@link #isHandled()} should be checked and made sure to be {@code false} before using the command.
 * {@link #handled} cannot be set to {@code false}, should be set to {@code true} using {@link #declareHandled()}, and is automatically updated to {@code true} once {@link #setReply(String)} is run.
 *
 * @author Victuracor, StarsDown64
 * @since 1.3.0
 */
public class UnrecognizedCommandEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private final String command;
    private final User author;
    private String reply;
    private boolean handled;

    public UnrecognizedCommandEvent(String command, User author)
    {
        super(true);
        this.command = command;
        this.author = author;
        this.reply = author.getAsMention() + ", that is not a valid command.";
    }

    @Override
    @NotNull
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    /**
     * Gets the command that was sent by the user.
     *
     * @return the command that was used.
     */
    public String getCommand()
    {
        return command;
    }

    /**
     * Gets the user that sent the command.
     *
     * @return the user that executed the command.
     */
    public User getAuthor()
    {
        return author;
    }

    /**
     * Gets the current reply to the unknown command.
     * By default this value is {@code getAuthorAsMention() + ", that is not a valid command."}
     *
     * @return the reply that will be sent.
     */
    public String getReply()
    {
        return reply;
    }

    /**
     * Sets a new reply to be sent to the author.
     * This will set {@link #handled} to {@code true}
     *
     * @throws IllegalArgumentException if {@code reply} is blank.
     * @param reply the new reply to send.
     */
    public void setReply(@Nonnull String reply)
    {
        if (reply.isBlank())
            throw new IllegalArgumentException("String reply is blank.");

        handled = true;
        this.reply = reply;
    }

    /**
     * Checks if the command has been handled by an external plugin yet.
     *
     * @return if the command has been handled yet.
     */
    public boolean isHandled()
    {
        return handled;
    }

    /**
     * Declares this unknown command has been handled.
     * For nearly all cases, plugins should declare it if they recognize the command.
     * {@link #handled} cannot be set back to {@code false}.
     */
    public void declareHandled()
    {
        handled = true;
    }

    /**
     * Gets the tag for the author.
     * Equivalent to calling {@code getAuthor().getAsTag()}
     * For those who do not want to reference JDA's library.
     *
     * @return the tag of the author.
     */
    public String getAuthorAsTag()
    {
        return author.getAsTag();
    }

    /**
     * Gets the mention string for the author.
     * Equivalent to calling {@code getAuthor().getAsMention()}
     * For those who do not want to reference JDA's library.
     *
     * @return the mention string of the author.
     */
    public String getAuthorAsMention()
    {
        return author.getAsMention();
    }
}
