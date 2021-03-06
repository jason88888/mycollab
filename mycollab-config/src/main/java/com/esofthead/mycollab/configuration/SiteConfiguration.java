/**
 * This file is part of mycollab-config.
 *
 * mycollab-config is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-config is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-config.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.configuration;

import com.esofthead.mycollab.spring.AppContextUtil;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimeZone;

import static com.esofthead.mycollab.configuration.ApplicationProperties.*;

/**
 * Utility class read mycollab system properties when system starts
 *
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class SiteConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(SiteConfiguration.class);

    private static SiteConfiguration instance;

    private String sentErrorEmail;
    private String siteName;
    private String serverAddress;
    private int serverPort;
    private EmailConfiguration emailConfiguration;
    private DatabaseConfiguration databaseConfiguration;
    private String cdnUrl;
    private String endecryptPassword;
    private String dropboxCallbackUrl;
    private String ggDriveCallbackUrl;
    private String appUrl;
    private String resourceDownloadUrl;

    private String facebookUrl;
    private String twitterUrl;
    private String googleUrl;
    private String linkedinUrl;
    private PullMethod pullMethod;

    public static void loadConfiguration() {
        TimeZone.setDefault(DateTimeZone.UTC.toTimeZone());
        DateTimeZone.setDefault(DateTimeZone.UTC);
        int serverPort = Integer.parseInt(System.getProperty(ApplicationProperties.MYCOLLAB_PORT, "8080"));
        ApplicationProperties.loadProps();
        instance = new SiteConfiguration();

        instance.sentErrorEmail = ApplicationProperties.getString(ERROR_SENDTO, "support@mycollab.com");
        instance.siteName = ApplicationProperties.getString(SITE_NAME, "MyCollab");
        instance.serverAddress = ApplicationProperties.getString(SERVER_ADDRESS, "localhost");
        instance.serverPort = serverPort;

        String pullMethodValue = ApplicationProperties.getString(ApplicationProperties.PULL_METHOD, "push");
        instance.pullMethod = PullMethod.valueOf(pullMethodValue);

        instance.cdnUrl = String.format(ApplicationProperties.getString(CDN_URL),
                instance.serverAddress, instance.serverPort);

        instance.appUrl = String.format(ApplicationProperties.getString(APP_URL),
                instance.serverAddress, instance.serverPort);
        if (!instance.appUrl.endsWith("/")) {
            instance.appUrl += "/";
        }

        instance.endecryptPassword = ApplicationProperties.getString(BI_ENDECRYPT_PASSWORD, "esofthead321");

        // load email
        String host = ApplicationProperties.getString(MAIL_SMTPHOST);
        String user = ApplicationProperties.getString(MAIL_USERNAME);
        String password = ApplicationProperties.getString(MAIL_PASSWORD);
        Integer port = Integer.parseInt(ApplicationProperties.getString(MAIL_PORT, "25"));
        Boolean isTls = Boolean.parseBoolean(ApplicationProperties.getString(MAIL_IS_TLS, "false"));
        Boolean isSsl = Boolean.parseBoolean(ApplicationProperties.getString(MAIL_IS_SSL, "false"));
        String noreplyEmail = ApplicationProperties.getString(MAIL_NOTIFY, "");
        instance.emailConfiguration = new EmailConfiguration(host, user, password, port, isTls, isSsl, noreplyEmail);

        // load database configuration
        String driverClass = ApplicationProperties.getString(DB_DRIVER_CLASS);
        String dbUrl = ApplicationProperties.getString(DB_URL);
        String dbUser = ApplicationProperties.getString(DB_USERNAME);
        String dbPassword = ApplicationProperties.getString(DB_PASSWORD);
        instance.databaseConfiguration = new DatabaseConfiguration(driverClass, dbUrl, dbUser, dbPassword);

        instance.resourceDownloadUrl = ApplicationProperties.getString(RESOURCE_DOWNLOAD_URL);
        if (!"".equals(instance.resourceDownloadUrl)) {
            instance.resourceDownloadUrl = String.format(instance.resourceDownloadUrl,
                    instance.serverAddress, instance.serverPort);
        } else {
            instance.resourceDownloadUrl = instance.appUrl + "file/";
        }

        instance.dropboxCallbackUrl = ApplicationProperties.getString(DROPBOX_AUTH_LINK);
        instance.ggDriveCallbackUrl = ApplicationProperties.getString(GOOGLE_DRIVE_LINK);

        instance.facebookUrl = ApplicationProperties.getString(FACEBOOK_URL, "https://www.facebook.com/mycollab2");
        instance.twitterUrl = ApplicationProperties.getString(TWITTER_URL, "https://twitter.com/mycollabdotcom");
        instance.googleUrl = ApplicationProperties.getString(GOOGLE_URL, "https://plus.google.com/u/0/b/112053350736358775306/+Mycollab/about/p/pub");
        instance.linkedinUrl = ApplicationProperties.getString(LINKEDIN_URL, "http://www.linkedin.com/company/mycollab");
    }

    private static SiteConfiguration getInstance() {
        if (instance == null) {
            loadConfiguration();
        }
        return instance;
    }

    public static String getCdnUrl() {
        return getInstance().cdnUrl;
    }

    public static String getAppUrl() {
        return getInstance().appUrl;
    }

    public static String getFacebookUrl() {
        return getInstance().facebookUrl;
    }

    public static String getTwitterUrl() {
        return getInstance().twitterUrl;
    }

    public static String getGoogleUrl() {
        return getInstance().googleUrl;
    }

    public static String getLinkedinUrl() {
        return getInstance().linkedinUrl;
    }

    public static String getResourceDownloadUrl() {
        return getInstance().resourceDownloadUrl;
    }

    public static DatabaseConfiguration getDatabaseConfiguration() {
        return getInstance().databaseConfiguration;
    }

    public static EmailConfiguration getEmailConfiguration() {
        return getInstance().emailConfiguration;
    }

    public static void setEmailConfiguration(EmailConfiguration conf) {
        getInstance().emailConfiguration = conf;
    }

    public static String getNotifyEmail() {
        return getInstance().emailConfiguration.getNotifyEmail();
    }

    public static String getDefaultSiteName() {
        return getInstance().siteName;
    }

    public static PullMethod getPullMethod() {
        return getInstance().pullMethod;
    }

    public static String getSendErrorEmail() {
        return getInstance().sentErrorEmail;
    }

    public static String getSiteUrl(String subDomain) {
        String siteUrl;
        IDeploymentMode modeService = AppContextUtil.getSpringBean(IDeploymentMode.class);
        if (modeService.isDemandEdition()) {
            siteUrl = String.format(ApplicationProperties.getString(ApplicationProperties.APP_URL), subDomain);
        } else {
            siteUrl = String.format(ApplicationProperties.getString(ApplicationProperties.APP_URL),
                    instance.serverAddress, instance.serverPort);
        }
        return siteUrl;
    }

    public static boolean isDemandEdition() {
        IDeploymentMode modeService = AppContextUtil.getSpringBean(IDeploymentMode.class);
        return modeService.isDemandEdition();
    }

    public static boolean isCommunityEdition() {
        IDeploymentMode modeService = AppContextUtil.getSpringBean(IDeploymentMode.class);
        return modeService.isCommunityEdition();
    }

    public static String getDropboxCallbackUrl() {
        return getInstance().dropboxCallbackUrl;
    }

    public static String getGGDriveCallbackUrl() {
        return getInstance().ggDriveCallbackUrl;
    }

    public static String getEnDecryptPassword() {
        return getInstance().endecryptPassword;
    }

    public static String getServerAddress() {
        return getInstance().serverAddress;
    }

    public static int getServerPort() {
        return getInstance().serverPort;
    }

    public enum PullMethod {
        push, pull
    }
}