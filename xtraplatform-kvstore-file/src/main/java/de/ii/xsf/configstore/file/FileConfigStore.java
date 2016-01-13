/**
 * Copyright 2016 interactive instruments GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ii.xsf.configstore.file;

import de.ii.xsf.configstore.api.KeyValueStore;
import de.ii.xsf.configstore.api.KeyNotFoundException;
import de.ii.xsf.configstore.api.Transaction;
import de.ii.xsf.configstore.api.WriteTransaction;
import de.ii.xtraserver.framework.util.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zahnen
 */
public class FileConfigStore implements KeyValueStore {

    private static final String INDEX_FILE_NAME = "index.properties";
    protected static final String ENCODING = "UTF-8";

    protected final File rootDir;

    public FileConfigStore(File rootDir) {
        this.rootDir = rootDir;
    }

    @Override
    public WriteTransaction<String> openWriteTransaction(String id) {
        return new WriteFileTransaction(getFile(id));
    }

    @Override
    public Transaction openDeleteTransaction(String id) {
        return new DeleteFileTransaction(getFile(id));
    }

    @Override
    public Reader getValueReader(String id) throws KeyNotFoundException, IOException {
        File cfg = getFile(id);
        if (!cfg.isFile()) {
            throw new KeyNotFoundException();
        }
        // FileReader uses system encoding, we don't want that
        return new InputStreamReader(new FileInputStream(cfg), ENCODING);
    }

    @Override
    public boolean containsKey(String id) {
        File cfg = getFile(id);

        return cfg.exists();
    }

    private File getFile(String id) {

        if (id.contains("/") && id.contains(":")) {
            String hash = StringUtils.createMD5Hash(id);
            addToIndex(id, hash);
            id = hash;
        }

        return new File(rootDir, id);
    }

    private void addToIndex(String origid, String hash) {

        //System.out.println(origid + " - " + hash);
        try {
            Properties props = new Properties();

            if (new File(rootDir, INDEX_FILE_NAME).exists()) {

                FileInputStream in = new FileInputStream(rootDir + "/" + INDEX_FILE_NAME);
                props.load(in);
                in.close();
            }

            props.setProperty(hash, origid);
            FileOutputStream out = new FileOutputStream(rootDir + "/" + INDEX_FILE_NAME);
            props.store(out, null);
        } catch (IOException ex) {
            Logger.getLogger(FileConfigStore.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<String> getKeys() {

        Properties props = new Properties();
        if (new File(rootDir, INDEX_FILE_NAME).exists()) {
            try {

                FileInputStream in = new FileInputStream(rootDir + "/" + INDEX_FILE_NAME);
                props.load(in);

                in.close();
            } catch (IOException ex) {
                Logger.getLogger(FileConfigStore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<String> files = new ArrayList<>();
        for (File f : rootDir.listFiles()) {
            if (f.isFile()
                    && !f.getAbsolutePath().endsWith(INDEX_FILE_NAME)
                    && !f.getAbsolutePath().endsWith("-custom")
                    && !f.getAbsolutePath().endsWith("-config")
                    && !f.getAbsolutePath().endsWith("-backup")) {

                if (!props.isEmpty()) {
                    files.add(props.getProperty(f.getName()));
                } else {
                    files.add(f.getName());
                }
            }
        }
        return files;
    }

    @Override
    public KeyValueStore getChildStore(String... path) {
        File configStore = new File(rootDir, path[0]);
        
        for (int i = 1; i < path.length; i++) {
            configStore = new File(configStore, path[i]);
        }
        
        if (!configStore.exists()) {
            configStore.mkdirs();
        }
        return new FileConfigStore(configStore);
    }

    @Override
    public List<String> getChildStoreIds() {
        List<String> files = new ArrayList<>();
        for (File f : rootDir.listFiles()) {
            if (f.isDirectory()) {
                files.add(f.getName());
            }
        }
        return files;
    }
}
