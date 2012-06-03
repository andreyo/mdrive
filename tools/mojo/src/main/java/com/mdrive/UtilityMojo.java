package com.mdrive;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 06.05.12
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class UtilityMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("UtilityMojo HELLO!");
    }
}