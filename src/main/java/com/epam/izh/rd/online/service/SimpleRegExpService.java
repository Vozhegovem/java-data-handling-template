package com.epam.izh.rd.online.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        String text = "";
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/sensitive_data.txt"))){
            text = reader.readLine();
            String regex = "(\\d{4})\\s(\\d{4}\\s\\d{4})\\s(\\d{4})";
            Pattern compile = Pattern.compile(regex);
            Matcher matcher = compile.matcher(text);
            while (matcher.find()) {
                text = text.replace(matcher.group(2), "**** ****");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String text = "";
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/sensitive_data.txt"))) {
            text = reader.readLine();
            Pattern pattern = Pattern.compile("(\\$\\{payment_amount}).*(\\$\\{balance})");
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                text = text.replace(matcher.group(1), Integer.toString((int)paymentAmount));
                text = text.replace(matcher.group(2), Integer.toString((int)balance));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
