using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Trivago.Forms
{
    public partial class OffreForm : Form
    {
        public OffreForm(String title, String agenceName, String chambreInfo, String clientInfo, String imageURL)
        {

            InitializeComponent();

            this.title.Text = title;
            this.agenceName.Text = agenceName;
            this.chambreInfo.Text = chambreInfo;
            this.nextButton.Text = "SUIVANT";
            this.clientInfo.Text = clientInfo;
            this.chambreImage.Load(imageURL);
        }

        private void NextButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void PictureBox2_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
