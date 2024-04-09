/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laravelcrud.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author to
 */
public class FileUtil {
    // get the file content to a string
    public static String toString(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        String content = new String(Files.readAllBytes(path));
        return content;
    }

    // separate to three a string
    public static String[] separateString(String origin, String begin, String end) {
        String[] separations = new String[3];

        int beginIndex = origin.indexOf(begin);
        int endIndex = origin.indexOf(end);

        separations[0] = origin.substring(0, beginIndex + begin.length());
        separations[1] = origin.substring(beginIndex + begin.length(), endIndex);
        separations[2] = origin.substring(endIndex, origin.length());

        return separations;
    }

    // get the file inner the jar
    public static String toStringInnerFile(String filePath) throws IOException {
        try (InputStream inputStream = FileUtil.class.getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            // Lisez le fichier ligne par ligne
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line + "\n");
            }

            return content.toString();
        } catch (IOException e) {
            throw e;
        }
    }

    // create a file with a content
    public static void createFileWithContent(String content, String destinationPath, String fileName) {
        try {
            // Créer le chemin complet du fichier
            String filePath = destinationPath + File.separator + fileName;

            // Créer un objet File pour représenter le fichier
            File file = new File(filePath);

            // Vérifier si le répertoire de destination existe, sinon le créer
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // Créer le fichier et écrire le contenu
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la création du fichier.");
        }
    }

    // remove a directory
    public static void removeDirectory(File dossier) {
        if (!dossier.exists()) {
            return;
        }

        if (dossier.isDirectory()) {
            // Liste tous les fichiers et sous-dossiers du dossier
            File[] fichiers = dossier.listFiles();

            if (fichiers != null) {
                for (File fichier : fichiers) {
                    // Appel récursif pour supprimer les fichiers/dossiers internes
                    removeDirectory(fichier);
                }
            }
        }

        // Supprime le dossier une fois que son contenu a été supprimé
        dossier.delete();
    }

    public static String getFirstLineWith(String origin, String target) {
        List<String> targetLines = new ArrayList<>();
        String[] lines = origin.split("\n");

        for (String line : lines) {
            if (line.contains(target)) {
                targetLines.add(line);
            }
        }

        if (targetLines.size() != 0) {
            return targetLines.get(0).trim();
        }

        return null;
    }
}
