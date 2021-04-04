namespace Trivago.Forms
{
    partial class OffreForm
    {
        private System.ComponentModel.IContainer components = null;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        private void InitializeComponent()
        {
            this.title = new System.Windows.Forms.Label();
            this.chambreImage = new System.Windows.Forms.PictureBox();
            this.chambreInfo = new System.Windows.Forms.Label();
            this.nextButton = new System.Windows.Forms.Label();
            this.clientInfo = new System.Windows.Forms.Label();
            this.agenceName = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.chambreImage)).BeginInit();
            this.SuspendLayout();
            // 
            // title
            // 
            this.title.AutoSize = true;
            this.title.Font = new System.Drawing.Font("Microsoft Sans Serif", 20F);
            this.title.Location = new System.Drawing.Point(11, 9);
            this.title.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.title.Name = "title";
            this.title.Size = new System.Drawing.Size(66, 31);
            this.title.TabIndex = 0;
            this.title.Text = "Title";
            // 
            // chambreImage
            // 
            this.chambreImage.Location = new System.Drawing.Point(16, 261);
            this.chambreImage.Margin = new System.Windows.Forms.Padding(2);
            this.chambreImage.Name = "chambreImage";
            this.chambreImage.Size = new System.Drawing.Size(999, 585);
            this.chambreImage.TabIndex = 1;
            this.chambreImage.TabStop = false;
            // 
            // chambreInfo
            // 
            this.chambreInfo.AutoSize = true;
            this.chambreInfo.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F);
            this.chambreInfo.Location = new System.Drawing.Point(11, 134);
            this.chambreInfo.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.chambreInfo.Name = "chambreInfo";
            this.chambreInfo.Size = new System.Drawing.Size(199, 25);
            this.chambreInfo.TabIndex = 2;
            this.chambreInfo.Text = "Détails de la chambre";
            // 
            // nextButton
            // 
            this.nextButton.AutoSize = true;
            this.nextButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 25F);
            this.nextButton.Location = new System.Drawing.Point(1182, 120);
            this.nextButton.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.nextButton.Name = "nextButton";
            this.nextButton.Size = new System.Drawing.Size(131, 39);
            this.nextButton.TabIndex = 4;
            this.nextButton.Text = "Suivant";
            this.nextButton.Click += new System.EventHandler(this.NextButton_Click);
            // 
            // clientInfo
            // 
            this.clientInfo.AutoSize = true;
            this.clientInfo.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F);
            this.clientInfo.Location = new System.Drawing.Point(399, 131);
            this.clientInfo.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.clientInfo.Name = "clientInfo";
            this.clientInfo.Size = new System.Drawing.Size(99, 25);
            this.clientInfo.TabIndex = 5;
            this.clientInfo.Text = "Info Client";
            // 
            // agenceName
            // 
            this.agenceName.AutoSize = true;
            this.agenceName.Font = new System.Drawing.Font("Microsoft Sans Serif", 21.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.agenceName.Location = new System.Drawing.Point(12, 70);
            this.agenceName.Name = "agenceName";
            this.agenceName.Size = new System.Drawing.Size(233, 33);
            this.agenceName.TabIndex = 6;
            this.agenceName.Text = "Nom de l\'agence";
            // 
            // OffreForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1443, 857);
            this.Controls.Add(this.agenceName);
            this.Controls.Add(this.clientInfo);
            this.Controls.Add(this.nextButton);
            this.Controls.Add(this.chambreInfo);
            this.Controls.Add(this.chambreImage);
            this.Controls.Add(this.title);
            this.Margin = new System.Windows.Forms.Padding(2);
            this.Name = "OffreForm";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.chambreImage)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label title;
        private System.Windows.Forms.PictureBox chambreImage;
        private System.Windows.Forms.Label chambreInfo;
        private System.Windows.Forms.Label nextButton;
        private System.Windows.Forms.Label clientInfo;
        private System.Windows.Forms.Label agenceName;
    }
}