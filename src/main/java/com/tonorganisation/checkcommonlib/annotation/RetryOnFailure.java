package com.tonorganisation.checkcommonlib.annotation;

import java.lang.annotation.*;

/**
 * Annotation utilisée pour réexécuter automatiquement une méthode en cas
 * d'échec.
 * 
 * <p>
 * Elle permet de spécifier :
 * <ul>
 * <li>le nombre maximal de tentatives ({@link #maxAttempts()})</li>
 * <li>le délai entre chaque tentative en millisecondes
 * ({@link #delayMs()})</li>
 * <li>les exceptions à inclure pour lesquelles le retry est activé
 * ({@link #include()})</li>
 * <li>les exceptions à exclure du mécanisme de retry ({@link #exclude()})</li>
 * </ul>
 * 
 * <p>
 * Cette annotation est généralement utilisée avec de l'AOP (Aspect Oriented
 * Programming) pour intercepter
 * les méthodes annotées et gérer le mécanisme de retry dynamiquement.
 * 
 * <p>
 * Exemple d'utilisation :
 * 
 * <pre>
 * {@code
 * @RetryOnFailure(maxAttempts = 5, delayMs = 1000, include = { IOException.class })
 * public void callRemoteService() {
 *     // ...
 * }
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryOnFailure {

    /**
     * Nombre maximal de tentatives avant d'abandonner.
     * 
     * @return le nombre de tentatives
     */
    int maxAttempts() default 3;

    /**
     * Délai (en millisecondes) entre deux tentatives.
     * 
     * @return le délai en millisecondes
     */
    long delayMs() default 500;

    /**
     * Liste des exceptions pour lesquelles le mécanisme de retry est appliqué.
     * Si vide, toutes les exceptions sont concernées sauf celles exclues.
     *
     * @return tableau de classes d'exception à inclure
     */
    Class<? extends Throwable>[] include() default {};

    /**
     * Liste des exceptions pour lesquelles le mécanisme de retry ne sera pas
     * appliqué.
     *
     * @return tableau de classes d'exception à exclure
     */
    Class<? extends Throwable>[] exclude() default {};
}
