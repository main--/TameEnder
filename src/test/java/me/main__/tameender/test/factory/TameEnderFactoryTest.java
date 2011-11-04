package me.main__.tameender.test.factory;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Collection;

import me.main__.tameender.TameEnder;
import me.main__.tameender.TamedEnderman;
import me.main__.tameender.TamedEndermanDefaultFactory;
import me.main__.tameender.TamedEndermanFactory;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.avaje.ebean.EbeanServer;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JavaPlugin.class)
public class TameEnderFactoryTest {
    public TamedEndermanFactory factory;
    public EbeanServer database;

    @Before
    public void setUp() throws Exception {
        // create our fake-stuff
        database = mock(EbeanServer.class);
        factory = new TamedEndermanDefaultFactory(database);

        Server mockServer = mock(Server.class);
        when(mockServer.getOfflinePlayer(anyString())).thenAnswer(new Answer<OfflinePlayer>() {
            public OfflinePlayer answer(InvocationOnMock invocation) throws Throwable {
                OfflinePlayer op = mock(OfflinePlayer.class);
                when(op.getName()).thenReturn((String) invocation.getArguments()[0]);
                return op;
            }
        });

        TameEnder plugin = PowerMockito.mock(TameEnder.class); // we want to mock some final methods
        // FileConfiguration fakeConfig = mock(FileConfiguration.class);
        // PluginManager fakePluginManager = mock(PluginManager.class);

        TameEnder.setInstance(plugin);
        when(plugin.getServer()).thenReturn(mockServer);
        when(plugin.getDatabase()).thenReturn(database);
        // when(plugin.getConfig()).thenReturn(fakeConfig);
        // when(mockServer.getPluginManager()).thenReturn(fakePluginManager);
    }

    @After
    public void tearDown() throws Exception {
        factory.clear();
    }

    @Test
    public void testMakeAndGet() {
        TamedEnderman e1 = factory.getNewOne("main()");
        TamedEnderman e2 = factory.getNewOne("main()");
        factory.getNewOne("tester");

        Collection<TamedEnderman> es = factory.getAll("main()");

        assertEquals(2, es.size());
        assertEquals(e1, es.toArray()[0]);
        assertEquals(e2, es.toArray()[1]);

        assertEquals(1, factory.getAll("tester").size());
        assertEquals(0, factory.getAll("nobody").size());
    }

    @Test
    public void testStorage() {
        TamedEnderman e = factory.getNewOne("main()");
        OfflinePlayer someOtherPlayer = mock(OfflinePlayer.class);
        when(someOtherPlayer.getName()).thenReturn("not main()");

        e.setOwner(someOtherPlayer);
        factory.save(e);
        Collection<TamedEnderman> main = factory.getAll("main()");
        Collection<TamedEnderman> notmain = factory.getAll("not main()");

        assertTrue(main.isEmpty());
        assertFalse(notmain.isEmpty());

        assertTrue(notmain.contains(e));
        assertEquals(1, notmain.size());
    }

    @Test
    public void testFlush() {
        TamedEnderman e = factory.getNewOne("main()");
        factory.flush();
        verify(database).save(e.getDatabaseEntity());
        verifyNoMoreInteractions(database);
    }
}
